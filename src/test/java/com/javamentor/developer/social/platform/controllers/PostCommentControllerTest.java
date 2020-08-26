package com.javamentor.developer.social.platform.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {
        "datasets/postCommentControllerTestDataset/comment/Comment.yml",
        "datasets/postCommentControllerTestDataset/comment/PostComment.yml",
        "datasets/postCommentControllerTestDataset/post/media.yml",
        "datasets/postCommentControllerTestDataset/post/post_media.yml",
        "datasets/postCommentControllerTestDataset/post/post_tags.yml",
        "datasets/postCommentControllerTestDataset/post/posts.yml",
        "datasets/postCommentControllerTestDataset/post/tags.yml",
        "datasets/postCommentControllerTestDataset/user/Active.yml",
        "datasets/postCommentControllerTestDataset/user/Role.yml",
        "datasets/postCommentControllerTestDataset/user/Status.yml",
        "datasets/postCommentControllerTestDataset/user/User.yml"
},
        cleanBefore = true)
class PostCommentControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addCommentToPost() throws Exception {
        CommentDto commentDto = CommentDto.builder()
                .userDto(UserDto.builder().userId(1L).build())
                .comment("my comment to")
                .build();

        String commentDtoJson = new Gson().toJson(commentDto);

        this.mockMvc.perform(post("/api/postsComments/{postId}/comment", "1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(commentDtoJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}