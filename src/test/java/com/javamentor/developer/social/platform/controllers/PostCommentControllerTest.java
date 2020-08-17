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
        "datasets/group/GroupWal.yml",
        "datasets/comment/Comment.yml",
        "datasets/comment/PostComment.yml"
},
        cleanBefore = true,
        cleanAfter = true
)
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
                .andExpect(status().isOk());
    }
}