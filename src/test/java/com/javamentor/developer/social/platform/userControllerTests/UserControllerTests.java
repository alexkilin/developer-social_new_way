package com.javamentor.developer.social.platform.userControllerTests;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/user/active.yml",
        "datasets/user/role.yml",
        "datasets/user/userFriends.yml",
        "datasets/user/language.yml",
        "datasets/user/user.yml",
        "datasets/user/user_languages.yml"
}, cleanBefore = true, cleanAfter = true)
public class UserControllerTests extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createUser() throws Exception {
        mockMvc.perform(post("/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"firstName\": \"Admin\"," +
                        "\"lastName\": \"LastName\"," +
                        "\"activeName\": \"ACTIVE\"," +
                        "\"email\": \"admin@admin.ru\"," +
                        "\"password\": \"Adminpass123\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Админ"))
                .andExpect(jsonPath("$.email").value("admin@admin.ru"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void findUserById() throws Exception {
        mockMvc.perform(get("/api/v2/users/{id}", 6L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(6))
                .andExpect(jsonPath("$.firstName").value("Admin8"))
                .andExpect(jsonPath("$.email").value("admin8@mail.ru"))
                .andExpect(jsonPath("$.roleName").value("USER"))
                .andExpect(jsonPath("$.aboutMe").value("My description about life - Admin8"))
                .andExpect(jsonPath("$.city").value("SPb"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.languages",hasSize(2)));
    }

    @Test
    void findUserInvalidId() throws Exception {
        this.mockMvc.perform(get("/api/user/{id}", 115L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 115 does not exist."));
    }

    @Test
    void findAllUsers() throws Exception {
        mockMvc.perform(get("/api/user/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void updateUser() throws Exception {
        this.mockMvc.perform(put("/api/v2/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"userId\": \"6\"," +
                        "\"firstName\": \"Update\"," +
                        "\"lastName\": \"LastName\"," +
                        "\"dateOfBirth\": \"02.05.1994\"," +
                        "\"education\": \"PTU\"," +
                        "\"aboutMe\": \"Some new information\"," +
                        "\"avatar\": \"www\"," +
                        "\"email\": \"Update@email.com\"," +
                        "\"city\": \"Msc\"," +
                        "\"linkSite\": \"myNewSite.ru\"," +
                        "\"profession\": \"Gamer\"," +
                        "\"status\": \"Pureness and perfection\"," +
                        "\"activeName\": \"ACTIVE\"" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("6"))
                .andExpect(jsonPath("$.firstName").value("Update"))
                .andExpect(jsonPath("$.email").value("Update@email.com"))
                .andExpect(jsonPath("$.city").value("Msc"))
                .andExpect(jsonPath("$.profession").value("Gamer"))
                .andExpect(jsonPath("$.activeName").value("ACTIVE"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.languages",hasSize(2)));

    }

    @Test
    void findUserFriends() throws Exception {
        mockMvc.perform(get("/api/user/getFriends/2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    public void deleteUserInvalidId() throws Exception {
        mockMvc.perform(delete("/api/user/delete/555")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with ID: 555 does not exist."));
    }

    @Test
    public void deleteUserOK() throws Exception {
        mockMvc.perform(delete("/api/user/delete/2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Пользователь с ID: 2 удалён успешно."));
    }

}