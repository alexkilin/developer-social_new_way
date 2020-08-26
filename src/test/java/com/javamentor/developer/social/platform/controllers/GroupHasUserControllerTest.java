package com.javamentor.developer.social.platform.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {
        "datasets/groupHasUserControllerTestDataset/group/Group.yml",
        "datasets/groupHasUserControllerTestDataset/group/GroupCategory.yml",
        "datasets/groupHasUserControllerTestDataset/group/GroupHasUser.yml",
        "datasets/groupHasUserControllerTestDataset/user/Active.yml",
        "datasets/groupHasUserControllerTestDataset/user/Role.yml",
        "datasets/groupHasUserControllerTestDataset/user/Status.yml",
        "datasets/groupHasUserControllerTestDataset/user/User.yml"
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
}