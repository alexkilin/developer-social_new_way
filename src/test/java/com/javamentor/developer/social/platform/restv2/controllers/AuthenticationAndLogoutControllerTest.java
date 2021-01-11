package com.javamentor.developer.social.platform.restv2.controllers;

import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.PrincipalDto;
import com.javamentor.developer.social.platform.models.dto.users.UserAuthorizationDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
