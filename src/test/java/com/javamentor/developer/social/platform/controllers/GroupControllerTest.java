package com.javamentor.developer.social.platform.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {
        "datasets/group/groups.yml",
        "datasets/audio/usersAudioTest/User.yml",
        "datasets/group/GroupCategory.yml",
        "datasets/audio/usersAudioTest/Active.yml",
        "datasets/audio/usersAudioTest/Role.yml",
        "datasets/audio/usersAudioTest/Status.yml"
},
        cleanBefore = true,
        cleanAfter = true)
public class GroupControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllGroups() throws Exception {
        this.mockMvc.perform(get("/api/groups/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void showGroup() {
    }

    @Test
    void showGroupWall() {
    }

    @Test
    void findGroupByName() {
    }
}