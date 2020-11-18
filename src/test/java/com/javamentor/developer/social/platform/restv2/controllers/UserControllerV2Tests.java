package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.*;
import com.javamentor.developer.social.platform.models.dto.users.UserRegisterDto;
import com.javamentor.developer.social.platform.models.dto.users.UserResetPasswordDto;
import com.javamentor.developer.social.platform.models.dto.users.UserUpdateInfoDto;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DataSet(value = {
        "datasets/restv2/user/language.yml",
        "datasets/restv2/user/user_languages.yml",
        "datasets/restv2/user/active.yml",
        "datasets/restv2/user/role.yml",
        "datasets/restv2/user/userFriends.yml",
        "datasets/restv2/user/user.yml"
}, cleanBefore = true, cleanAfter = true)
public class UserControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private final Gson gson = new Gson();



    @Test
    void createUser() throws Exception {
        UserRegisterDto userDto = UserRegisterDto.builder()
                .email("admin@admin.ru")
                .password("AdminPwd123")
                .firstName("AdminFirstName")
                .lastName("AdminLastName")
                .build();

        mockMvc.perform(post(apiUrl + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@admin.ru"))
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("AdminFirstName"))
                .andExpect(jsonPath("$.lastName").value("AdminLastName"))
                .andExpect(jsonPath("$.activeName").value("ACTIVE"))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post(apiUrl + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "User with email: admin@admin.ru already exist. Email should be unique"));
    }

    @Test
    void updateUser() throws Exception {
        UserUpdateInfoDto userDto = UserUpdateInfoDto.builder()
                .userId(2L)
                .firstName("NewName")
                .lastName("NewLastName")
                .email("newadmin123@admin.ru")
                .build();

        mockMvc.perform(put(apiUrl + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("NewName"))
                .andExpect(jsonPath("$.lastName").value("NewLastName"))
                .andExpect(jsonPath("$.email").value("newadmin123@admin.ru"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void changeUserPassword() throws Exception {
        String pwd = "ocheSlozhniParol111";
        UserResetPasswordDto userResetPasswordDto = UserResetPasswordDto.builder()
                .password(pwd)
                .build();

        mockMvc.perform(patch(apiUrl + "/2/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(userResetPasswordDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Password changed for user 2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Assert.assertTrue("Сравнение с паролем в базе, подтверждение изменения",
                passwordEncoder.matches(pwd, userService.getById(2L).get().getPassword()));
    }

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get(apiUrl + "/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))

                .andExpect(jsonPath("$[0].userId").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Admin1"))
                .andExpect(jsonPath("$[0].lastName").value("LastNameAdmin1"))
                .andExpect(jsonPath("$[0].dateOfBirth").value("30.05.2008"))
                .andExpect(jsonPath("$[0].education").value("MIT University"))
                .andExpect(jsonPath("$[0].aboutMe").value("My description about life - Admin1"))
                .andExpect(jsonPath("$[0].avatar").value("www.myavatar0.ru/9090"))
                .andExpect(jsonPath("$[0].email").value("admin2@user.ru"))
                .andExpect(jsonPath("$[0].password").value("userpass0"))
                .andExpect(jsonPath("$[0].city").value("SPb"))
                .andExpect(jsonPath("$[0].linkSite").isEmpty())
                .andExpect(jsonPath("$[0].profession").value("Plumber"))
                .andExpect(jsonPath("$[0].roleName").value("USER"))
                .andExpect(jsonPath("$[0].status").value("Pureness and perfection"))
                .andExpect(jsonPath("$[0].activeName").value("ACTIVE"))

                .andExpect(jsonPath("$[1].userId").value(3))
                .andExpect(jsonPath("$[2].userId").value(4))
                .andExpect(jsonPath("$[3].userId").value(5))
                .andExpect(jsonPath("$[4].userId").value(6));
    }

    @Test
    void updateStatus() throws Exception {
        StatusDto statusDto = StatusDto.builder()
                .userId(2L)
                .status("Outer space exploration")
                .build();

        mockMvc.perform(patch(apiUrl + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(statusDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Outer space exploration"));

        statusDto = StatusDto.builder()
                .userId(222L)
                .status("Outer space exploration")
                .build();

        mockMvc.perform(patch(apiUrl + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(statusDto)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 222 does not exist."));
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
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("User with ID: 2 does not exist."));
//    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get(apiUrl + "/{userId}", 2L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(2))
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

        mockMvc.perform(get(apiUrl + "/{userId}", 222L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 222 does not exist."));
    }

    @Test
    void getFriendsOfUserById() throws Exception {
        mockMvc.perform(get(apiUrl + "/{userId}/friends", 2L)
                .param("currentPage", "0")
                .param("itemsOnPage", "5"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));

        mockMvc.perform(get(apiUrl + "/{userId}/friends", 222L)
                .param("currentPage", "0")
                .param("itemsOnPage", "5"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 222 does not exist."));
    }

}
