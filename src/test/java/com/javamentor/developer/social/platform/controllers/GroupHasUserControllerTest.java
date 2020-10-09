package com.javamentor.developer.social.platform.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {
        "datasets/grouphasuserset/group/Group.yml",
        "datasets/grouphasuserset/group/GroupCategory.yml",
        "datasets/grouphasuserset/group/GroupHasUser.yml",
        "datasets/grouphasuserset/user/Active.yml",
        "datasets/grouphasuserset/user/Role.yml",
        "datasets/grouphasuserset/user/User.yml"
},
        cleanBefore = true)
class GroupHasUserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userJoinGroup() throws Exception {
        this.mockMvc.perform(post("/api/groupsHasUsers/add?groupId=5&userId=1"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteUserByIdOfGroup() throws Exception {
        this.mockMvc.perform(delete("/api/groupsHasUsers/delete?groupId=5&userId=5"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}