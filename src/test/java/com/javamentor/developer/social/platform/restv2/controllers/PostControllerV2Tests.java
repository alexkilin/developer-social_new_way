package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostCreateDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.TopicDto;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.Repost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/restv2/post/postResources/bookmarks.yml" ,
        "datasets/restv2/post/postResources/like.yml" ,
        "datasets/restv2/post/postResources/media.yml" ,
        "datasets/restv2/post/postTest/post_media.yml",
        "datasets/restv2/post/postTest/post_tags.yml",
        "datasets/restv2/post/postResources/posts.yml" ,
        "datasets/restv2/post/postResources/topics.yml" ,
        "datasets/restv2/post/postResources/tags.yml" ,
        "datasets/restv2/post/postResources/comments.yml" ,
        "datasets/restv2/post/postTest/post_comment.yml",
        "datasets/restv2/post/postTest/post_like.yml",
        "datasets/restv2/post/usersResources/Role.yml",
        "datasets/restv2/post/usersResources/User.yml",
        "datasets/restv2/post/usersResources/Active.yml",
        "datasets/restv2/post/usersResources/Friends.yml",
        "datasets/restv2/post/groupResources/Group.yml",
        "datasets/restv2/post/groupResources/GroupHasUser.yml",
        "datasets/restv2/post/groupResources/GroupWal.yml"
}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before_post.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
public class PostControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/post";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;

    Gson gson = new Gson();

    @Test
    public void getAllPosts() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(8))
                .andExpect(jsonPath("$.items[0].title").value("Title1"))
                .andExpect(jsonPath("$.items[0].text").value("Text1"))
                .andExpect(jsonPath("$.items[2].title").value("Title3"))
                .andExpect(jsonPath("$.items[2].text").value("Text3"));
    }

    @Test
    public void getPostsByFriendsAndGroups() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts/friends/groups")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].title").value("Title5"))
                .andExpect(jsonPath("$.items[0].text").value("Text5"))
                .andExpect(jsonPath("$.items[2].title").value("Title5"))
                .andExpect(jsonPath("$.items[2].text").value("Text5"));

        mockMvc.perform(get(apiUrl + "/posts/friends/groups")
                .param("currentPage", "5")
                .param("itemsOnPage", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid pagination parameters. Parameter 'currentPage' value [5] is greater than total number of available pages [1] considering parameter 'itemsOnPage' value [10]"));
    }

    @Test
    public void getPostsByTag() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts")
                .param("currentPage", "1")
                .param("itemsOnPage", "10")
                .param("tag", "Tag"))
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].text").value("Tag"));
    }

    @Test
    public void getPostsByUserId() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts/user/{id}", 202)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].title").value("Title5"))
                .andExpect(jsonPath("$.items[0].text").value("Text5"));
    }

    @Test
    public void addPost() throws Exception {
        List<TagDto> tag = new ArrayList<>();
        List<MediaPostDto> media = new ArrayList<>();

        TopicDto topicDto = TopicDto.builder()
                .topic("MyNewTopic")
                .build();

        PostCreateDto postCreateDto = PostCreateDto.builder()
                .text("MyTextTest")
                .title("MyTitleTest")
                .tags(tag)
                .userId(200l)
                .topic(topicDto)
                .media(media)
                .build();

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andExpect(status().isOk());

        tag.add(TagDto.builder()
                .text("MyText")
                .build());
        postCreateDto.setTags(tag);

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andExpect(status().isOk());

        media.add(MediaPostDto.builder()
                .mediaType("AUDIO")
                .url("MyUrl1.ru")
                .userId(200l)
                .build());
        postCreateDto.setMedia(media);

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andExpect(status().isOk());

        media.add(MediaPostDto.builder()
                .mediaType("IMAGE")
                .url("MyUrl2.ru")
                .userId(200l)
                .build());

        media.add(MediaPostDto.builder()
                .mediaType("VIDEO")
                .url("MyUrl3.ru")
                .userId(200l)
                .build());
        postCreateDto.setMedia(media);

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andExpect(status().isOk());
        List list =  entityManager.createQuery("SELECT a from Post a where a.title like :title and a.text like :text")
                .setParameter("title", "MyTitleTest")
                .setParameter("text", "MyTextTest")
                .getResultList();
        assertEquals(4, list.size());
    }

    @Test
    public void deletePost() throws Exception {
        mockMvc.perform(delete(apiUrl + "/post/{id}", 201)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{id}", 1000)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Can't find Post with ID 1000"));
    }

    @Test
    public void showPostComments() throws Exception {
        mockMvc.perform(get(apiUrl + "/post/{postId}/comments", 200)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].comment").value("comment 1"));
    }

    @Test
    public void addCommentToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/comment", 200)
                .content("Such a bad comment"))
                .andExpect(status().isCreated());
        PostComment postComment = (PostComment) entityManager.createQuery("SELECT a from PostComment a " +
                "join fetch a.comment c join fetch a.post p where c.comment like :comment")
                .setParameter("comment", "Such a bad comment")
                .getSingleResult();
        assertEquals(200, postComment.getPost().getId());
    }

    @Test
    public void addLikeToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/like", 201))
                .andExpect(status().isCreated());

        Post post =  (Post) entityManager.createQuery("SELECT a from Post a join fetch " +
                "a.postLikes pl join fetch pl.like l join fetch l.user where a.id=201")
                .getSingleResult();
        Optional<PostLike> postLike = post.getPostLikes().stream().filter(x-> x.getLike().getUser().getUserId()==65)
                .findFirst();
        assertTrue(postLike.isPresent());

        mockMvc.perform(post(apiUrl + "/post/{postId}/like", 201))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Like has already been added"));
    }

    @Test
    public void deleteLikeFromPost() throws Exception {
        mockMvc.perform(delete(apiUrl + "/post/{postId}/like", 200))
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{postId}/like", 200))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Like already been removed"));
    }

    @Test
    public void addPostToBookmark() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/bookmark", 200))
                .andExpect(status().isCreated());
        Bookmark bookmark = (Bookmark) entityManager.createQuery("SELECT a from Bookmark a " +
                "join fetch a.post p where a.user.userId = 65 and p.id = 200").getSingleResult();
        assertEquals("Title1", bookmark.getPost().getTitle());

        mockMvc.perform(post(apiUrl + "/post/{postId}/bookmark", 202))
                .andExpect(status().isCreated());
        Bookmark bookmark2 = (Bookmark) entityManager.createQuery("SELECT a from Bookmark a " +
                "join fetch a.post p where a.user.userId = 65 and p.id = 202").getSingleResult();
        assertEquals("Title3", bookmark2.getPost().getTitle());

        mockMvc.perform(post(apiUrl + "/post/{postId}/bookmark", 204))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Post has already been added to the bookmark"));
    }

    @Test
    public void deletePostFromBookmark() throws Exception {

        mockMvc.perform(delete(apiUrl + "/post/{postId}/bookmark", 204))
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{postId}/bookmark", 204))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Post has already been removed from the bookmark"));
    }

    @Test
    public void addRepostToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/repost", 200))
                .andExpect(status().isCreated());

        Post post =  (Post) entityManager.createQuery("SELECT a from Post a join fetch a.reposts r join fetch r.user u where a.id=200")
                .getSingleResult();
        Optional<Repost> repost = post.getReposts().stream().filter(x-> x.getUser().getUserId()==65).findFirst();
        assertTrue(repost.isPresent());
    }

    @Test
    public void getAllBookmarkedPosts() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts/bookmarks")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));
    }

    @Test
    public void getAllPostsByTopic() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts/topic")
                .param("topic", "MyTopicName2")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].id").value(205))
                .andExpect(jsonPath("$.items[0].firstName").value("Admin65"))
                .andExpect(jsonPath("$.items[1].id").value(206))
                .andExpect(jsonPath("$.items[1].title").value("Title5"))
                .andExpect(jsonPath("$.totalResults").value(3));

        mockMvc.perform(get(apiUrl + "/posts/topic")
                .param("topic", "Nothing")
                .param("currentPage", "1")
                .param("itemsOnPage", "1"))
                .andExpect(status().isBadRequest());
    }
}