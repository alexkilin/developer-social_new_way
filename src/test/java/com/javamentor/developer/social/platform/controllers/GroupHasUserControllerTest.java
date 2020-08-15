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
        "datasets/group/Group.yml",
        "datasets/audio/usersAudioTest/User.yml",
        "datasets/group/GroupCategory.yml",
        "datasets/audio/usersAudioTest/Active.yml",
        "datasets/audio/usersAudioTest/Role.yml",
        "datasets/audio/usersAudioTest/Status.yml",
        "datasets/group/GroupHasUser.yml",
        "datasets/post/media.yml",
        "datasets/post/post_media.yml",
        "datasets/post/post_tags.yml",
        "datasets/post/posts.yml",
        "datasets/post/tags.yml",
        "datasets/group/GroupWal.yml"
},
        cleanBefore = true,
        cleanAfter = true)
class GroupHasUserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userJoinGroup() throws Exception {
        this.mockMvc.perform(post("/api/groupsHasUsers/add?groupId=5&userId=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}