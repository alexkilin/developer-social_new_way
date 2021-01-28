package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.*;
import com.javamentor.developer.social.platform.models.dto.users.UserRegisterDto;
import com.javamentor.developer.social.platform.models.dto.users.UserResetPasswordDto;
import com.javamentor.developer.social.platform.models.dto.users.UserUpdateInfoDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@DataSet(value = {
        "datasets/restv2/user/userResources/language.yml" ,
        "datasets/restv2/user/userResources/user_languages.yml" ,
        "datasets/restv2/user/userResources/Active.yml" ,
        "datasets/restv2/user/userResources/Role.yml" ,
        "datasets/restv2/user/userResources/userFriends.yml" ,
        "datasets/restv2/user/userResources/User.yml"
}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
public class UserControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/users";

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private final Gson gson = new Gson();

    @Test
    void createNewUser() throws Exception {
        UserRegisterDto userDto = UserRegisterDto.builder()
                .email("jm.platform.noreply@gmail.com")
                .password("AdminPwd123")
                .firstName("AdminFirstName")
                .lastName("AdminLastName")
                .build();

        mockMvc.perform(post(apiUrl + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("jm.platform.noreply@gmail.com"))
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("AdminFirstName"))
                .andExpect(jsonPath("$.lastName").value("AdminLastName"))
                .andExpect(jsonPath("$.activeName").value("ACTIVE"))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Assert.assertNotNull(userService.getByEmail("jm.platform.noreply@gmail.com").get());

        String oldDtoLastName = userDto.getLastName();
        userDto.setLastName("NewAdminLastName");
        mockMvc.perform(post(apiUrl + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        String.format("User with email '%s' already registered. Email should be unique", userDto.getEmail()))
                );

        Assert.assertEquals("Фамилия пользователя должна была остаться прежней",
                oldDtoLastName, userService.getByEmail(userDto.getEmail()).get().getLastName());
    }

    @Test
    void updateUser() throws Exception {
        UserUpdateInfoDto userDto = UserUpdateInfoDto.builder()
                .userId(200L)
                .firstName("NewName")
                .lastName("NewLastName")
                .email("newadmin123@admin.ru")
                .build();

        mockMvc.perform(put(apiUrl + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("NewName"))
                .andExpect(jsonPath("$.lastName").value("NewLastName"))
                .andExpect(jsonPath("$.email").value("newadmin123@admin.ru"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void changeUserPassword() throws Exception {
        String pwd = "ocheSlozhniParol111";
        Long userId = 200L;
        UserResetPasswordDto userResetPasswordDto = UserResetPasswordDto.builder()
                .password(pwd)
                .build();

        User preUpdatedUser = (User) entityManager
                .createQuery("select a from User a where a.userId = :userId")
                .setParameter("userId", userId)
                .getSingleResult();

        String oldPassword = preUpdatedUser.getPassword();

        mockMvc.perform(patch(apiUrl + "/{userId}/password", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userResetPasswordDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password changed for user 200"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        User updatedUser = (User) entityManager
                .createQuery("select a from User a where a.userId = :userId")
                .setParameter("userId", userId)
                .getSingleResult();

        String newPassword = updatedUser.getPassword();

        Assert.assertTrue("Сравнение с паролем в базе, подтверждение изменения" ,
                passwordEncoder.matches(pwd , newPassword));

        Assert.assertNotEquals("Comparing old password with new password", oldPassword, newPassword);
    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get(apiUrl + "/")
                .param("currentPage" , "1")
                .param("itemsOnPage" , "5"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(5));
    }

    @Test
    void updateStatus() throws Exception {
        StatusDto statusDto = StatusDto.builder()
                .userId(200L)
                .status("Outer space exploration")
                .build();

        mockMvc.perform(patch(apiUrl + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(statusDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Outer space exploration"));

        statusDto = StatusDto.builder()
                .userId(1000L)
                .status("Outer space exploration")
                .build();

        mockMvc.perform(patch(apiUrl + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(statusDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 1000 does not exist."));
    }

//    @Test
//    void deleteUser() throws Exception {
//        mockMvc.perform(delete(apiUrl + "/{userId}", 2L))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("User with ID: 2 deleted"));
//
//        mockMvc.perform(delete(apiUrl + "/{userId}", 2L))
//                .andDo(print())
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("User with ID: 2 does not exist."));
//    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get(apiUrl + "/{userId}" , 200L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(200))
                .andExpect(jsonPath("$.firstName").value("Admin1"))
                .andExpect(jsonPath("$.lastName").value("LastNameAdmin1"))
                .andExpect(jsonPath("$.dateOfBirth").value("30.05.2008"))
                .andExpect(jsonPath("$.education").value("MIT University"))
                .andExpect(jsonPath("$.aboutMe").value("My description about life - Admin1"))
                .andExpect(jsonPath("$.avatar").value("www.myavatar0.ru/9090"))
                .andExpect(jsonPath("$.email").value("admin2@user.ru"))
                .andExpect(jsonPath("$.password").value("userpass0"))
                .andExpect(jsonPath("$.city").value("SPb"))
                .andExpect(jsonPath("$.linkSite").isEmpty())
                .andExpect(jsonPath("$.profession").value("Plumber"))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(jsonPath("$.status").value("Pureness and perfection"))
                .andExpect(jsonPath("$.activeName").value("ACTIVE"));

        mockMvc.perform(get(apiUrl + "/{userId}" , 1000L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 1000 does not exist."));
    }

    @Test
    void getFriendsOfUserById() throws Exception {
        mockMvc.perform(get(apiUrl + "/{userId}/friends" , 200L)
                .param("currentPage" , "1")
                .param("itemsOnPage" , "5"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));

        mockMvc.perform(get(apiUrl + "/{userId}/friends" , 1000L)
                .param("currentPage" , "1")
                .param("itemsOnPage" , "5"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 1000 does not exist."));
    }

    @Test
    void GetAllUsersWithFilters() throws Exception {
        mockMvc.perform(get(apiUrl + "/GetAllUsersWithFilters")
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10")
                .param("education" , "Harvard"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].firstName").value("Admin0"));

        mockMvc.perform(get(apiUrl + "/GetAllUsersWithFilters")
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10")
                .param("city" , "SPb"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(5));

        mockMvc.perform(get(apiUrl + "/GetAllUsersWithFilters")
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10")
                .param("city" , "SPb")
                .param("profession" , "Creator"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));

        mockMvc.perform(get(apiUrl + "/GetAllUsersWithFilters")
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10")
                .param("startDateOfBirth" , "1990-05-30")
                .param("endDateOfBirth" , "2012-03-30"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(6));

        mockMvc.perform(get(apiUrl + "/GetAllUsersWithFilters")
                .param("currentPage" , "1")
                .param("itemsOnPage" , "10")
                .param("endDateOfBirth" , "2003-12-30"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2));
    }

}