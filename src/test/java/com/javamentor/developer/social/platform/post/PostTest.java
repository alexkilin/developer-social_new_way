package com.javamentor.developer.social.platform.post;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/post/usersPostsTest/active.yml",
        "datasets/post/usersPostsTest/role.yml",
        "datasets/post/usersPostsTest/user.yml",
        "datasets/post/posts.yml",
        "datasets/post/usersPostsTest/user_tabs.yml",
        "datasets/post/tags.yml",
        "datasets/post/post_tags.yml",
        "datasets/post/bookmarks.yml",
        "datasets/post/groups.yml",
        "datasets/post/group_category.yml",
        "datasets/post/group_has_user.yml",
        "datasets/post/group_wal.yml",
        "datasets/post/media.yml",
        "datasets/post/likes.yml",
        "datasets/post/post_media.yml"}, cleanBefore = true, cleanAfter = true)
public class PostTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @Test
    public void getPostByTag() throws Exception {
        this.mockMvc.perform(get("/api/posts/{text}", "Tag"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void getAllPosts() throws Exception {
        this.mockMvc.perform(get("/api/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    public void addPost() throws Exception {
        PostDto postCreateDto = PostDto.builder()
                .title("title_1")
                .text("text_1").build();

        this.mockMvc.perform(post("/api/posts/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCommentsByIdPost() throws Exception {

        this.mockMvc.perform(get("/api/posts/{id}/comments", 1))
                .andDo(print())
                .andExpect(status().isOk());

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
    public void deletePostById() throws Exception {
        this.mockMvc.perform(delete("/api/posts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Post with ID 1, is successful"));
    }
}
