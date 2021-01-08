package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostCreateDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.TopicDto;
import com.javamentor.developer.social.platform.models.entity.comment.Comment;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.Repost;
import com.javamentor.developer.social.platform.models.entity.user.User;
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

@Sql(statements = {
        "Insert into active(id, name) values(3, 'test')",
        "Insert into role(id, name) values(1, 'USER')",

        "Insert into Users(user_id,first_name, last_name, email, last_redaction_date, persist_date, active_id, role_id) " +
                "values (65,'user666','user666', 'admin666@user.ru', '2020-08-04 16:42:03.157535', '2020-08-04 16:42:03.157535', 3, 1)",
})
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
@DataSet(value = {
        "datasets/restv2/post/bookmarks.yml",
        "datasets/restv2/post/like.yml",
        "datasets/restv2/post/media.yml",
        "datasets/restv2/post/postTest/post_media.yml",
        "datasets/restv2/post/postTest/post_tags.yml",
        "datasets/restv2/post/posts.yml",
        "datasets/restv2/post/topics.yml",
        "datasets/restv2/post/tags.yml",
        "datasets/restv2/post/comments.yml",
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(7))
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3))
                .andExpect(jsonPath("$.items[0].title").value("Title5"))
                .andExpect(jsonPath("$.items[0].text").value("Text5"))
                .andExpect(jsonPath("$.items[2].title").value("Title1"))
                .andExpect(jsonPath("$.items[2].text").value("Text1"));

        mockMvc.perform(get(apiUrl + "/posts/friends/groups")
                .param("currentPage", "5")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid pagination parameters. Parameter 'currentPage' value [5] is greater than total number of available pages [1] considering parameter 'itemsOnPage' value [10]"));
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
                .userId(50l)
                .topic(topicDto)
                .media(media)
                .build();

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        tag.add(TagDto.builder()
                .text("MyText")
                .build());
        postCreateDto.setTags(tag);

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        media.add(MediaPostDto.builder()
                .mediaType("AUDIO")
                .url("MyUrl1.ru")
                .userId(50l)
                .build());
        postCreateDto.setMedia(media);

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());

        media.add(MediaPostDto.builder()
                .mediaType("IMAGE")
                .url("MyUrl2.ru")
                .userId(50l)
                .build());

        media.add(MediaPostDto.builder()
                .mediaType("VIDEO")
                .url("MyUrl3.ru")
                .userId(50l)
                .build());
        postCreateDto.setMedia(media);

        mockMvc.perform(post(apiUrl + "/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postCreateDto)))
                .andDo(print())
                .andExpect(status().isOk());
        List list =  entityManager.createQuery("SELECT a from Post a where a.title like :title and a.text like :text")
                .setParameter("title", "MyTitleTest")
                .setParameter("text", "MyTextTest")
                .getResultList();
        assertEquals(4, list.size());
    }

    @Test
    public void deletePost() throws Exception {
        mockMvc.perform(delete(apiUrl + "/post/{id}", 40)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{id}", 400)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Can't find Post with ID 400"));
    }

    @Test
    public void showPostComments() throws Exception {
        mockMvc.perform(get(apiUrl + "/post/{postId}/comments", 100)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].comment").value("comment 1"));
    }

    @Test
    public void addCommentToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/comment", 100)
                .content("Such a bad comment"))
                .andDo(print())
                .andExpect(status().isCreated());
        PostComment postComment = (PostComment) entityManager.createQuery("SELECT a from PostComment a where a.comment.comment like :comment")
                .setParameter("comment", "Such a bad comment")
                .getSingleResult();
        assertEquals(100, postComment.getPost().getId());
    }

    @Test
    public void addLikeToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/like", 100))
                .andDo(print())
                .andExpect(status().isCreated());

        Post post =  (Post) entityManager.createQuery("SELECT a from Post a where a.id=100")
                .getSingleResult();
        Optional<PostLike> postLike = post.getPostLikes().stream().filter(x-> x.getLike().getUser().getUserId()==65).findFirst();
        assertTrue(postLike.isPresent());

        mockMvc.perform(post(apiUrl + "/post/{postId}/like", 100))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Like has already been added"));
    }

    @Test
    public void deleteLikeFromPost() throws Exception {
        mockMvc.perform(delete(apiUrl + "/post/{postId}/like", 20))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{postId}/like", 20))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Like already been removed"));
    }

    @Test
    public void addPostToBookmark() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/bookmark", 100))
                .andDo(print())
                .andExpect(status().isCreated());
        Bookmark bookmark = (Bookmark) entityManager.createQuery("SELECT a from Bookmark a where a.user.userId = 65 and a.post.id = 100").getSingleResult();
        assertEquals("Title1", bookmark.getPost().getTitle());

        mockMvc.perform(post(apiUrl + "/post/{postId}/bookmark", 30))
                .andDo(print())
                .andExpect(status().isCreated());
        Bookmark bookmark2 = (Bookmark) entityManager.createQuery("SELECT a from Bookmark a where a.user.userId = 65 and a.post.id = 30").getSingleResult();
        assertEquals("Title3", bookmark2.getPost().getTitle());

        mockMvc.perform(post(apiUrl + "/post/{postId}/bookmark", 100))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Post has already been added to the bookmark"));
    }

    @Test
    public void deletePostFromBookmark() throws Exception {
        mockMvc.perform(delete(apiUrl + "/post/{postId}/bookmark", 20))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/post/{postId}/bookmark", 20))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The Post has already been removed from the bookmark"));
    }

    @Test
    public void addRepostToPost() throws Exception {
        mockMvc.perform(post(apiUrl + "/post/{postId}/repost", 100))
                .andDo(print())
                .andExpect(status().isCreated());

        Post post =  (Post) entityManager.createQuery("SELECT a from Post a where a.id=100")
                .getSingleResult();
        Optional<Repost> repost = post.getReposts().stream().filter(x-> x.getUser().getUserId()==65).findFirst();
        assertTrue(repost.isPresent());
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

    @Test
    public void getAllPostsByTopic() throws Exception {
        mockMvc.perform(get(apiUrl + "/posts/topic")
                .param("topic", "MyTopicName2")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(65))
                .andExpect(jsonPath("$.items[0].firstName").value("Admin0"))
                .andExpect(jsonPath("$.items[1].id").value(70))
                .andExpect(jsonPath("$.items[1].title").value("Title5"))
                .andExpect(jsonPath("$.totalResults").value(2));

        mockMvc.perform(get(apiUrl + "/posts/topic")
                .param("topic", "Nothing")
                .param("currentPage", "1")
                .param("itemsOnPage", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}