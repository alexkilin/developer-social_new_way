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
        "datasets/groupset/user/User.yml",
        "datasets/groupset/user/Active.yml",
        "datasets/groupset/user/Role.yml",
        "datasets/groupset/group/GroupHasUser.yml",
        "datasets/groupset/post/media.yml",
        "datasets/groupset/post/post_media.yml",
        "datasets/groupset/post/post_tags.yml",
        "datasets/groupset/post/posts.yml",
        "datasets/groupset/post/tags.yml",
        "datasets/groupset/group/GroupWal.yml"
},
        cleanBefore = true)
class GroupControllerTest extends AbstractIntegrationTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void getAllGroups() throws Exception {
//        this.mockMvc.perform(get("/api/groups/all?page=1&size=3"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(3));
//    }
//
//    @Test
//    void showGroup() throws Exception {
//        this.mockMvc.perform(get("/api/groups/{id}", "1"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(1))
//                .andExpect(jsonPath("name").value("JAVA IS 0"))
//                .andExpect(jsonPath("lastRedactionDate").value("2020-08-13T11:46:35.493"))
//                .andExpect(jsonPath("persistDate").value("2020-08-13T11:46:35.493"))
//                .andExpect(jsonPath("linkSite").value("www.groupsite0.ru"))
//                .andExpect(jsonPath("groupCategory").value("Programming"))
//                .andExpect(jsonPath("ownerFio").value("LastNameUser0 Admin0"))
//                .andExpect(jsonPath("description").value("This is a description of the group #0"));
//    }
//
//    @Test
//    void showGroupWall() throws Exception {
//        this.mockMvc.perform(get("/api/groups/{id}/posts?page=1&size=2", "1"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2));
//    }
//
//    @Test
//    void findGroupByName() throws Exception {
//        this.mockMvc.perform(get("/api/groups/name?name=JAVA IS 0"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("id").value(1))
//                .andExpect(jsonPath("name").value("JAVA IS 0"))
//                .andExpect(jsonPath("groupCategory").value("Programming"))
//                .andExpect(jsonPath("subscribers").value(1));
//    }
}