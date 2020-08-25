package com.javamentor.developer.social.platform.post;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {
        "datasets/post/usersPostsTest/active.yml",
        "datasets/post/usersPostsTest/role.yml",
        "datasets/post/usersPostsTest/status.yml",
        "datasets/post/usersPostsTest/user.yml",
        "datasets/post/usersPostsTest/user_tabs.yml",
        "datasets/post/posts.yml",
        "datasets/post/tags.yml",
        "datasets/post/post_tags.yml",
        "datasets/post/media.yml",
        "datasets/post/post_media.yml"
}, cleanBefore = true, cleanAfter = true)
public class PostTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllPosts() throws Exception {
        this.mockMvc.perform(get("/api/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    public void getPostByTag() throws Exception {
        this.mockMvc.perform(get("/api/posts/{text}", "Tag"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void getPostByInvalidTag() throws Exception {
        this.mockMvc.perform(get("/api/posts/{text}", "TagTag"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void getPostByUserId() throws Exception {
        this.mockMvc.perform(get("/api/posts/user/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void getPostByInvalidUserId() throws Exception {
        this.mockMvc.perform(get("/api/posts/user/{id}", "100"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
