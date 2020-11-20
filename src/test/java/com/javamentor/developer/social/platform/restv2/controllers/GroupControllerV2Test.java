package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {
        "datasets/restv2/user/User.yml",
        "datasets/restv2/user/Active.yml",
        "datasets/restv2/user/Role.yml",
        "datasets/restv2/groupset/group/Group.yml",
        "datasets/restv2/groupset/group/GroupHasUser.yml",
        "datasets/restv2/groupset/group/GroupWal.yml",
        "datasets/restv2/groupset/group/GroupCategory.yml",
        "datasets/restv2/post/media.yml",
        "datasets/restv2/post/post_media.yml",
        "datasets/restv2/post/post_tags.yml",
        "datasets/restv2/post/posts.yml",
        "datasets/restv2/post/tags.yml"}, cleanBefore = true, cleanAfter = true)
public class GroupControllerV2Test extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllGroups() throws Exception {
        mockMvc.perform(get("/api/v2/groups?page=1&size=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void showGroup() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("JAVA IS 0"))
                .andExpect(jsonPath("lastRedactionDate").value("2020-08-13T11:46:35.493"))
                .andExpect(jsonPath("persistDate").value("2020-08-13T11:46:35.493"))
                .andExpect(jsonPath("linkSite").value("www.groupsite0.ru"))
                .andExpect(jsonPath("groupCategory").value("Programming"))
                .andExpect(jsonPath("ownerFio").value("LastNameUser0 Admin0"))
                .andExpect(jsonPath("description").value("This is a description of the group #0"));
    }

    @Test
    void showGroupInvalidId() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}", 100))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group id 100 not found"));
    }

    @Test
    void showGroupWall() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/posts", 1)
                .param("page", "1")
                .param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void showGroupWallInvalidId() throws Exception {
        this.mockMvc.perform(get("/api/v2/groups/{groupId}/posts?page=1&size=2", 100))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void findGroupByName() throws Exception {
        mockMvc.perform(get("/api/v2/groups/name?name=JAVA IS 0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("JAVA IS 0"))
                .andExpect(jsonPath("groupCategory").value("Programming"))
                .andExpect(jsonPath("subscribers").value(1));
    }

    @Test
    void findGroupByInvalidName() throws Exception {
        mockMvc.perform(get("/api/v2/groups/name?name=JavaGroupTest"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group name JavaGroupTest not found"));
    }

    @Test
    void getUsersFromTheGroup() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/users?page=1&size=2", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getUsersFromTheGroupInvalidId() throws Exception {
        mockMvc.perform(get("/api/v2/groups/{groupId}/users?page=1&size=2", 100))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Group id 100 not found"));
    }

    @Test
    void userJoinGroup() throws Exception {
        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=2", 5))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 2 added to the group with id: 5"));
    }

    @Test
    void userJoinGroupExist() throws Exception {
        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=2", 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 2 already a member of the group with id: 2"));
    }


    @Test
    void userJoinGroupInvalidId() throws Exception {
        mockMvc.perform(put("/api/v2/groups/{groupId}/users?userId=100", 100))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v2/groups/{groupId}/users?userId=6", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User with id: 6 is no longer a member of the group with id: 1"));
    }

    @Test
    void deleteUserByInvalidId() throws Exception {
        mockMvc.perform(delete("/api/v2/groups/{groupId}/users?userId=1", 100))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}
