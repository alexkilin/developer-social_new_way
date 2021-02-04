package com.javamentor.developer.social.platform.restv2.controllers;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import org.hamcrest.text.MatchesPattern;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {
        "datasets/restv2/video/usersResources/Active.yml",
        "datasets/restv2/video/usersResources/User.yml",
        "datasets/restv2/video/usersResources/Role.yml",
        "datasets/restv2/video/videoResources/UsersVideosCollections.yml" ,
        "datasets/restv2/video/videoResources/Media.yml" ,
        "datasets/restv2/video/albumVideoTest/VideoAlbum.yml",
        "datasets/restv2/video/albumVideoTest/Album.yml",
        "datasets/restv2/video/albumVideoTest/UserHasAlbum.yml",
        "datasets/restv2/video/albumVideoTest/AlbumHasVideo.yml",
        "datasets/restv2/video/videoResources/Video.yml",
        "datasets/restv2/video/videoResources/like.yml",
        "datasets/restv2/video/albumVideoTest/media_like.yml"
}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
class VideosControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/video";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    Gson gson = new Gson();

    @Test
    public void getPartVideos() throws Exception {
        mockMvc.perform(get(apiUrl + "/")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(5))
                .andExpect(jsonPath("$.items[0].id").value(200))
                .andExpect(jsonPath("$.items[0].url").value("www.myvideo1.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 0"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 0"))
                .andExpect(jsonPath("$.items[1].id").value(201))
                .andExpect(jsonPath("$.items[2].id").value(203))
                .andExpect(jsonPath("$.items[3].id").value(204));
    }

    @Test
    public void getVideoOfNamePart() throws Exception {
        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "2")
                .param("itemsOnPage", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(6))
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(202))
                .andExpect(jsonPath("$.items[0].url").value("www.myvideo3.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon2"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 2"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 2"))
                .andExpect(jsonPath("$.items[1].id").value(203));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "1")
                .param("itemsOnPage", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(6))
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(200))
                .andExpect(jsonPath("$.items[0].url").value("www.myvideo1.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 0"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 0"))
                .andExpect(jsonPath("$.items[1].id").value(201));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "3")
                .param("itemsOnPage", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(6))
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(204))
                .andExpect(jsonPath("$.items[0].url").value("www.myVideo1.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon4"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 4"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 4"))
                .andExpect(jsonPath("$.items[1].id").value(205));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "2")
                .param("itemsOnPage", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(6))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(205))
                .andExpect(jsonPath("$.items[0].url").value("www.myImage1.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon5"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 5"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 5"));
    }

    @Test
    public void getVideoOfNamePartInvalidParams() throws Exception {
        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "-2")
                .param("itemsOnPage", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile(
                        ".*make sure that parameters 'currentPage' and 'itemsOnPage' values are greater.*"))));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "2")
                .param("itemsOnPage", "-2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile(
                        ".*make sure that parameters 'currentPage' and 'itemsOnPage' values are greater.*"))));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "0")
                .param("itemsOnPage", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile(
                        ".*make sure that parameters 'currentPage' and 'itemsOnPage' values are greater.*"))));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "20")
                .param("itemsOnPage", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile("" +
                        ".*make sure that parameters 'currentPage' and 'itemsOnPage' values are greater.*"))));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "3")
                .param("itemsOnPage", "3"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile(
                        ".*is greater than total number of available pages.*"))));
    }

    @Test
    public void getPartVideoOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/video", 200)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3));
    }

    @Test
    public void getVideoSortedByLikes() throws Exception {
        String result = mockMvc.perform(get(apiUrl + "/order/like")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(202))
                .andExpect(jsonPath("$.items[1].id").value(200))
                .andExpect(jsonPath("$.items[2].id").value(201))
                .andExpect(jsonPath("$.items.length()").value(6))
                .andReturn().getResponse().getContentAsString();

        PageDto<Object, Object> actualPage = objectMapper.readValue(result, PageDto.class);

        Map<Object, Object> firstEntityMap = (LinkedHashMap<Object, Object>) actualPage.getItems().get(0);
        Integer firstId = (Integer) firstEntityMap.get("id");

        Long firstCountOfLikes = (Long) entityManager.createQuery("select count (ml.like.id)" +
                " from Videos v left join MediaLike ml on v.id = ml.media.id where v.id =: id")
                .setParameter("id", firstId.longValue())
                .getSingleResult();
        Assert.assertEquals(3L, (long) firstCountOfLikes);

        Map<Object, Object> secondEntityMap = (LinkedHashMap<Object, Object>) actualPage.getItems().get(1);
        Integer secondId = (Integer) secondEntityMap.get("id");

        Long secondCountOfLikes = (Long) entityManager.createQuery("select count (ml.like.id)" +
                " from Videos v left join MediaLike ml on v.id = ml.media.id where v.id =: id")
                .setParameter("id", secondId.longValue())
                .getSingleResult();
        Assert.assertEquals(2L, (long) secondCountOfLikes);

        Map<Object, Object> thirdEntityMap = (LinkedHashMap<Object, Object>) actualPage.getItems().get(2);
        Integer thirdId = (Integer) thirdEntityMap.get("id");

        Long thirdCountOfLikes = (Long) entityManager.createQuery("select count (ml.like.id)" +
                " from Videos v left join MediaLike ml on v.id = ml.media.id where v.id =: id")
                .setParameter("id", thirdId.longValue())
                .getSingleResult();
        Assert.assertEquals(1L, (long) thirdCountOfLikes);
    }

    @Test
    public void addVideo() throws Exception {
        VideoDto videoDto = VideoDto.builder()
                .author("MyAuthor33")
                .icon("MyIcon33")
                .name("MyNewVideo")
                .url("www.myVideo.ru")
                .build();

        String responseContent = mockMvc.perform(post(apiUrl + "/user/{userId}/video", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(videoDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value("www.myVideo.ru"))
                .andReturn().getResponse().getContentAsString();

        VideoDto createdVideoDto = objectMapper.readValue(responseContent, VideoDto.class);

        Videos video = (Videos) entityManager.createQuery("SELECT a from Videos a where a.id = :id")
                .setParameter("id", createdVideoDto.getId())
                .getSingleResult();
        assertEquals("MyAuthor33", video.getAuthor());

        mockMvc.perform(post(apiUrl + "/user/{userId}/video", 1000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(videoDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id 1000 is not found"));
    }

    @Test
    public void createVideoAlbum() throws Exception {
        AlbumVideoDto albumVideoDto = AlbumVideoDto.builder()
                .icon("MyNewIconTest")
                .name("MyVideoAlbumTest")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 1000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id 1000 is not found"));

        String resultContent = mockMvc.perform(post(apiUrl + "/user/{userId}/album", 210)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyVideoAlbumTest"))
                .andReturn().getResponse().getContentAsString();

        AlbumVideoDto createdAlbumVideoDto = objectMapper.readValue(resultContent, AlbumVideoDto.class);

        AlbumVideo album = (AlbumVideo) entityManager.createQuery("SELECT a from AlbumVideo a join fetch a.album a2 where a2.id = :id")
                .setParameter("id", createdAlbumVideoDto.getId())
                .getSingleResult();
        assertEquals("MyVideoAlbumTest", album.getAlbum().getName());
        assertEquals(com.javamentor.developer.social.platform.models.entity.media.MediaType.VIDEO, album.getAlbum().getMediaType());

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 203)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Video album with name 'MyVideoAlbumTest' already exists"));
    }

    @Test
    public void addExistVideoInAlbum() throws Exception{
        Long albumId = 200L;
        Long existVideoId = 200L;

        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", String.valueOf(albumId))
                .param("videoId", String.valueOf(existVideoId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format("Image with id %s is already in album with id %s", existVideoId,albumId)));
    }

    @Test
    public void addInAlbums() throws Exception {
        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "200")
                .param("videoId", "202"))
                .andExpect(status().isOk())
                .andExpect(content().string("Video id 202 added to album id 200"));

        AlbumVideo albumVideo = (AlbumVideo) entityManager.createQuery("SELECT a from AlbumVideo a join fetch a.videos where a.id = 200")
                .getSingleResult();
        Set<Videos> videoSet = albumVideo.getVideos();
        assertEquals(3,videoSet.size());
        Optional<Videos> video200 = albumVideo.getVideos().stream().filter(a -> a.getId() == 200).findFirst();
        Optional<Videos> video201 = albumVideo.getVideos().stream().filter(a -> a.getId() == 201).findFirst();
        Optional<Videos> video202 = albumVideo.getVideos().stream().filter(a -> a.getId() == 202).findFirst();
        assertTrue(video200.isPresent());
        assertTrue(video201.isPresent());
        assertTrue(video202.isPresent());


        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "1000")
                .param("videoId", "200"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Video album with id 1000 is not found"));

        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "200")
                .param("videoId", "1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Video with id 1000 is not found"));
    }

    @Test
    public void getAllAlbums() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/album", 200)
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2));
    }

    @Test
    public void getFromAlbumOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/album/{albumId}/video", 200)
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2));
    }

    @Test
    public void getAlbumVideoOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/video", 201)
                .param("album", "videoAlbum3")
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));
    }
}