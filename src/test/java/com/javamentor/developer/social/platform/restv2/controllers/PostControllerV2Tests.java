package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostCreateDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {
        "datasets/restv2/post/bookmarks.yml",
        "datasets/restv2/post/like.yml",
        "datasets/restv2/post/media.yml",
        "datasets/restv2/post/post_media.yml",
        "datasets/restv2/post/post_tags.yml",
        "datasets/restv2/post/posts.yml",
        "datasets/restv2/post/tags.yml",
        "datasets/restv2/post/comments.yml",
        "datasets/restv2/post/post_comment.yml",
        "datasets/restv2/post/usersResources/Role.yml",
        "datasets/restv2/post/usersResources/User.yml",
        "datasets/restv2/post/usersResources/Active.yml"
}, cleanBefore = true, cleanAfter = true)
public class PostControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/post";

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @Test
    public void getAudioOfAuthor() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(6))
                .andExpect(jsonPath("$.items[0].title").value("Title1"))
                .andExpect(jsonPath("$.items[0].text").value("Text1"))
                .andExpect(jsonPath("$.items[2].title").value("Title3"))
                .andExpect(jsonPath("$.items[2].text").value("Text3"));
    }

    @Test
    public void getPostsByTag() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts")
                .param("currentPage", "1")
                .param("itemsOnPage", "10")
                .param("tag", "Tag"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Title1"))
                .andExpect(jsonPath("$.items[0].text").value("Text1"));
    }

    @Test
    public void getAllTags() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts/tags")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].text").value("Tag"));
    }

    @Test
    public void getPostsByUserId() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts/user/{id}", 60)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Title1"))
                .andExpect(jsonPath("$.items[0].text").value("Text1"));
    }

    /*
    Падает с ошибкой создание поста
     */
    @Test
    public void addPost() throws Exception {
        TagDto tagDto = TagDto.builder()
                .text("MyNewTag")
                .build();

        List<TagDto> tag = new ArrayList<>();
        tag.add(tagDto);

        MediaPostDto mediaDto = MediaPostDto.builder()
                .mediaType("MyMedia")
                .url("MyUrl")
                .userId(50l)
                .build();

        List<MediaPostDto> media = new ArrayList<>();
        media.add(mediaDto);

        PostCreateDto postCreateDto = PostCreateDto.builder()
                .text("MyText")
                .title("MyTitle")
                .tags(tag)
                .userId(50l)
                .media(media)
                .build();

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deletePost() throws Exception {
        mockMvc.perform(delete(apiUrl + "/post/{id}", 4l)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{id}", 400l)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Can't find Post with ID 400"));
    }

    @Test
    public void showPostComments() throws Exception {
        mockMvc.perform(get(apiUrl + "/post/{postId}/comments", 1)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].comment").value("comment 1"));
    }

    @Test
    public void addCommentToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/comment", 1)
                .param("comment", "MyNewComment"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[1].comment").value("MyNewComment"));
    }

    @Test
    public void addLikeToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/like", 1))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post(apiUrl + "/post/{postId}/like", 1))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Like has already been added"));
    }

    @Test
    public void deleteLikeFromPost() throws Exception {
        mockMvc.perform(delete(apiUrl + "/post/{postId}/like", 1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{postId}/like", 1))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Like has already been removed"));
    }

    @Test
    public void addPostToBookmark() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/bookmark", 1))
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(post(apiUrl + "/post/{postId}/bookmark", 1))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Post has already been added to the bookmark"));
    }

    @Test
    public void deletePostFromBookmark() throws Exception {
        mockMvc.perform(delete(apiUrl + "/post/{postId}/bookmark", 1))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{postId}/bookmark", 1))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Post has already been removed from the bookmark"));
    }

    @Test
    public void addRepostToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/repost", 1))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getAllBookmarkedPosts() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts/bookmarks")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));
    }
}
