package com.javamentor.developer.social.platform.controllers.v1;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {
        "datasets/groupset/user/User.yml",
        "datasets/groupset/user/Active.yml",
        "datasets/groupset/user/Role.yml",
        "datasets/groupset/group/Group.yml",
        "datasets/groupset/group/GroupHasUser.yml",
        "datasets/groupset/group/GroupWal.yml",
        "datasets/groupset/group/GroupCategory.yml",
        "datasets/groupset/post/media.yml",
        "datasets/groupset/post/post_media.yml",
        "datasets/groupset/post/post_tags.yml",
        "datasets/groupset/post/posts.yml",
        "datasets/groupset/post/tags.yml"}, cleanBefore = true, cleanAfter = true)
class GroupControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllGroups() throws Exception {
        this.mockMvc.perform(get("/api/groups/all?page=1&size=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void showGroup() throws Exception {
        this.mockMvc.perform(get("/api/groups/{id}", "1"))
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
    void showGroupWall() throws Exception {
        this.mockMvc.perform(get("/api/groups/{id}/posts?page=1&size=2", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void findGroupByName() throws Exception {
        this.mockMvc.perform(get("/api/groups/name?name=JAVA IS 0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("JAVA IS 0"))
                .andExpect(jsonPath("groupCategory").value("Programming"))
                .andExpect(jsonPath("subscribers").value(1));
    }

    @Test
    void getUsersFromTheGroup() throws Exception {
        this.mockMvc.perform(get("/api/groups/name?name=JAVA IS 0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("JAVA IS 0"))
                .andExpect(jsonPath("groupCategory").value("Programming"))
                .andExpect(jsonPath("subscribers").value(1));
    }

    @Test
    public void showGroupInvalidId() throws Exception {
        this.mockMvc.perform(get("/api/groups/{id}", 100L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Группа с id 100 не найдена"));
    }

    @Test
    void findGroupByInvalidName() throws Exception {
        this.mockMvc.perform(get("/api/groups/name?name=JavaGroupTest"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Группа с именем JavaGroupTest не найдена"));
    }

    @Test
    void getUsersFromTheGroupInvalidId() throws Exception {
        this.mockMvc.perform(get("/api/groups/{id}/users", 100L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Группа с id 100 не найдена"));
    }

    @Test
    void showGroupWallInvalidId() throws Exception {
        this.mockMvc.perform(get("/api/groups/{id}/posts?page=1&size=2", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }


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

    @Test
    void userJoinGroupInvalidId() throws Exception {
        this.mockMvc.perform(post("/api/groupsHasUsers/add?groupId=100&userId=100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteUserByInvalidIdOfGroup() throws Exception {
        this.mockMvc.perform(delete("/api/groupsHasUsers/delete?groupId=100&userId=100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}