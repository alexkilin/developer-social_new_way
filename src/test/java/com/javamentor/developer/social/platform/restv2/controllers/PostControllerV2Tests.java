package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {
        "datasets/restv2/post/bookmarks.yml",
        "datasets/restv2/post/like.yml",
        "datasets/restv2/post/media.yml",
        "datasets/restv2/post/post_media.yml",
        "datasets/restv2/post/post_tags.yml",
        "datasets/restv2/post/posts.yml",
        "datasets/restv2/post/tags.yml",
        "datasets/restv2/post/usersResources/Role.yml",
        "datasets/restv2/post/usersResources/User.yml",
        "datasets/restv2/post/usersResources/Active.yml"
}, cleanBefore = true, cleanAfter = true)
public class PostControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/post";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAudioOfAuthor() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(6));
//                .andExpect(jsonPath("$.items[0].id").value(20))
//                .andExpect(jsonPath("$.items[0].name").value("AudioTestName 1"))
//                .andExpect(jsonPath("$.items[0].length").value(365))
//                .andExpect(jsonPath("$.items[0].album").value("AlbumTestName 1"))
//                .andExpect(jsonPath("$.items[0].icon").value("TestIcon1"));
    }
}
