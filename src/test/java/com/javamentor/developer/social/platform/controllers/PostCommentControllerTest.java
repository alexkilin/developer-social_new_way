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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {
        "datasets/postcommentset/comment/Comment.yml",
        "datasets/postcommentset/comment/PostComment.yml",
        "datasets/postcommentset/post/media.yml",
        "datasets/postcommentset/post/post_media.yml",
        "datasets/postcommentset/post/post_tags.yml",
        "datasets/postcommentset/post/posts.yml",
        "datasets/postcommentset/post/tags.yml",
        "datasets/postcommentset/user/Active.yml",
        "datasets/postcommentset/user/Role.yml",
        "datasets/postcommentset/user/User.yml"
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

        this.mockMvc.perform(post("/api/postsComments/{postId}/comment", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(commentDtoJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Пользователь с id: 1 добавил комментарий в пост с id: 1"));
    }
}