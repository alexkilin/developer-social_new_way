package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.PrincipalDto;
import com.javamentor.developer.social.platform.models.dto.users.UserAuthorizationDto;
import com.javamentor.developer.social.platform.models.dto.users.UserRegisterDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Данный Sql-стэйтмент можно хранить в отдельном .sql файле, учитывая, что он везде одинаковый.
 * Задать фазу использования перед методом
 * executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
 * А после метода очищать данные(см. второй @Sql).
 * Это дает более чистые и независимые друг о друга тесты.
 */
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(statements = {"delete from Users" , "delete from active" , "delete from role"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthenticationAndLogoutControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecurityHelper securityHelper;

    @Autowired
    private UserService userService;

    private final Gson gson = new Gson();

    private final String apiUrl = "/auth";


    @Test
    void createAuthenticationToken() throws Exception {
        UserAuthorizationDto user666 = UserAuthorizationDto.builder()
                .email("admin666@user.ru")
                .password("user666")
                .build();

        MockHttpServletResponse response = mockMvc.perform(post(apiUrl + "/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user666)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        User currentUserDetails = User.builder()
                .email("admin666@user.ru")
                .build();

        String responseJwt = response.getContentAsString();

        boolean validateToken = securityHelper.validateToken(responseJwt , currentUserDetails);
        assertTrue(validateToken);

    }

    @Test
    void createAuthenticationTokenWrongEmail() throws Exception {
        UserAuthorizationDto invalidEmail = UserAuthorizationDto.builder()
                .email("НЕСУЩЕСТВУЮЩИЙ")
                .password("ПОЛЬЗОВАТЕЛЬ")
                .build();

        MockHttpServletResponse invalidEmailResponse = mockMvc.perform(post(apiUrl + "/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(invalidEmail)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String correctResponse = "User with this email does not exist";
        assertEquals(correctResponse, invalidEmailResponse.getContentAsString());

    }

    @Test
    void createAuthenticationTokenWrongPassword() throws Exception {
        UserAuthorizationDto invalidPassword = UserAuthorizationDto.builder()
                .email("admin666@user.ru")
                .password("НЕВЕРНЫЙ ПАРОЛЬ")
                .build();

        MockHttpServletResponse invalidPasswordResponse = mockMvc.perform(post(apiUrl + "/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(invalidPassword)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        String correctResponse = "Wrong password, try again";
        assertEquals(correctResponse, invalidPasswordResponse.getContentAsString());
    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
    void getPrincipal() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(get(apiUrl + "/principal"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertNotNull(response.getContentAsString());
        PrincipalDto responseContent = gson.fromJson(response.getContentAsString() , PrincipalDto.class);

        assertEquals("admin666@user.ru" , responseContent.getEmail());

    }


    @Test
    @WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
    void makeLogout() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(post("/logout").header("Authorization", "Bearer "))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertNull(response.getHeader("Authorization"));

        MockHttpServletResponse checkAccessIsForbidden = mockMvc.perform(get(apiUrl + "/principal"))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse();

        String errorMessage = "Access Denied";
        assertEquals(errorMessage, checkAccessIsForbidden.getErrorMessage());
    }

    @Test
    @DataSet(value = {
            "datasets/restv2/user/userResources/Active.yml"
    }, strategy = SeedStrategy.REFRESH, cleanAfter = true)
    void registerUserAndConfirmEmail() throws Exception {
        UserRegisterDto userDto = UserRegisterDto.builder()
                .email("jm.platform.noreply@gmail.com")
                .password("AdminPwd123")
                .firstName("AdminFirstName")
                .lastName("AdminLastName")
                .build();

        String registerUrl = apiUrl + "/reg";

        mockMvc.perform(post(registerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto))
                .with(anonymous()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("jm.platform.noreply@gmail.com"))
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("AdminFirstName"))
                .andExpect(jsonPath("$.lastName").value("AdminLastName"))
                .andExpect(jsonPath("$.activeName").value("ACTIVE"))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Assert.assertFalse("Проверка аттрибута подтверждения почты нового пользователя",
                userService.getByEmail("jm.platform.noreply@gmail.com").get().isEnabled());

        userDto.setLastName("NewAdminLastName");
        mockMvc.perform(post(registerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto))
                .with(anonymous()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("jm.platform.noreply@gmail.com"))
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("AdminFirstName"))
                .andExpect(jsonPath("$.lastName").value("NewAdminLastName"))
                .andExpect(jsonPath("$.activeName").value("ACTIVE"))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        final String verificationUrl = "/auth/reg/confirm";

        // Действительный токен пользователя jm.platform.noreply@gmail.com от 2021-01-13 03:57:17 UTC
        // при времени жизни токена 100 лет
        String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqbS5wbGF0Zm9ybS5ub3JlcGx5QGdtYWlsLmNvbSIsImlhdCI6MTY" +
                "xMDUxMDIzNywiZXhwIjo0NzY2MjcwMjM3fQ.qOfFk9VWWespOKqR1ScyPD0mrZ3qavlGz3j7sRs_xcw";
        mockMvc.perform(post(verificationUrl)
                .contentType(MediaType.TEXT_PLAIN)
                .content(validToken)
                .with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("jm.platform.noreply@gmail.com"))
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("AdminFirstName"))
                .andExpect(jsonPath("$.lastName").value("NewAdminLastName"))
                .andExpect(jsonPath("$.activeName").value("ACTIVE"))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Assert.assertTrue("Проверка аттрибута подтверждения почты нового пользователя после подтвеждения",
                userService.getByEmail("jm.platform.noreply@gmail.com").get().isEnabled());

        mockMvc.perform(post(verificationUrl)
                .contentType(MediaType.TEXT_PLAIN)
                .content(validToken)
                .with(anonymous()))
                .andExpect(status().is(HttpStatus.CONFLICT.value()))
                .andExpect(content().string("User have been activated earlier"));

        mockMvc.perform(post(registerUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User with email 'jm.platform.noreply@gmail.com' already registered. Email should be unique"));

        // Просроченный токен пользователя jm.platform.noreply@gmail.com от 2021-01-13 03:48:12 UTC
        // при времени жизни токена 3 cекунды
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqbS5wbGF0Zm9ybS5ub3JlcGx5QGdtYWlsLmNvbSIsImlhdCI6MTY" +
                "xMDUwOTY5MiwiZXhwIjoxNjEwNTA5Njk1fQ.CnUS3Ae5NQngF1_CBnYGFLEHI2GcDS8CQ2t4UMZ1uKA";
        mockMvc.perform(post(verificationUrl)
                .contentType(MediaType.TEXT_PLAIN)
                .content(expiredToken)
                .with(anonymous()))
                .andExpect(status().is(HttpStatus.REQUEST_TIMEOUT.value()))
                .andExpect(content().string("Registration code is outdated"));

        // Токен несуществующего пользователя some.invalid.email@gmail.com от 2021-01-13 04:37:08 UTC
        // при времени жизни токена 100 лет
        String unknownUserToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb21lLmludmFsaWQuZW1haWxAZ21haWwuY29tIiwiaWF0Ijox" +
                "NjEwNTEyNjI4LCJleHAiOjQ3NjYyNzI2Mjh9.x5jwfSXL9eXr3nlomc9RG0kIAWYjs49Buey_RMY0CKU";
        mockMvc.perform(post(verificationUrl)
                .contentType(MediaType.TEXT_PLAIN)
                .content(unknownUserToken)
                .with(anonymous()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format("No matching user record found for verification code '%s'", unknownUserToken)));

        // Некорректный токен
        String notAToken = "eyJ";
        mockMvc.perform(post(verificationUrl)
                .contentType(MediaType.TEXT_PLAIN)
                .content(notAToken)
                .with(anonymous()))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Registration code is corrupted"));
    }
}
