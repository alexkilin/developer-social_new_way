package com.javamentor.developer.social.platform.restv2.controllers;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.Set;
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
        "datasets/restv2/video/UsersVideosCollections.yml",
        "datasets/restv2/video/Media.yml",
        "datasets/restv2/video/albumVideoTest/VideoAlbum.yml",
        "datasets/restv2/video/albumVideoTest/Album.yml",
        "datasets/restv2/video/albumVideoTest/UserHasAlbum.yml",
        "datasets/restv2/video/albumVideoTest/AlbumHasVideo.yml",
        "datasets/restv2/video/Video.yml"}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
class VideosControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/video";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;

    Gson gson = new Gson();

    @Test
    public void getPartVideos() throws Exception {
        mockMvc.perform(get(apiUrl + "/")
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(5))
                .andExpect(jsonPath("$.items[0].id").value(10))
                .andExpect(jsonPath("$.items[0].url").value("www.myvideo1.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 0"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 0"))
                .andExpect(jsonPath("$.items[1].id").value(20))
                .andExpect(jsonPath("$.items[2].id").value(40))
                .andExpect(jsonPath("$.items[3].id").value(50));
    }

    @Test
    public void getVideoOfNamePart() throws Exception {
        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "2")
                .param("itemsOnPage", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(6))
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(30))
                .andExpect(jsonPath("$.items[0].url").value("www.myvideo3.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon2"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 2"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 2"))
                .andExpect(jsonPath("$.items[1].id").value(40));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "1")
                .param("itemsOnPage", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(6))
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(10))
                .andExpect(jsonPath("$.items[0].url").value("www.myvideo1.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 0"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 0"))
                .andExpect(jsonPath("$.items[1].id").value(20));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "3")
                .param("itemsOnPage", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(6))
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].id").value(50))
                .andExpect(jsonPath("$.items[0].url").value("www.myVideo1.ru"))
                .andExpect(jsonPath("$.items[0].icon").value("TestIcon4"))
                .andExpect(jsonPath("$.items[0].name").value("VideoTestName 4"))
                .andExpect(jsonPath("$.items[0].author").value("TestAuthor 4"))
                .andExpect(jsonPath("$.items[1].id").value(60));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "2")
                .param("itemsOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalResults").value(6))
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].id").value(60))
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
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile(
                        ".*make sure that parameters 'currentPage' and 'itemsOnPage' values are greater.*"))));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "2")
                .param("itemsOnPage", "-2"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile(
                        ".*make sure that parameters 'currentPage' and 'itemsOnPage' values are greater.*"))));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "0")
                .param("itemsOnPage", "2"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile(
                        ".*make sure that parameters 'currentPage' and 'itemsOnPage' values are greater.*"))));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "20")
                .param("itemsOnPage", "0"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile("" +
                        ".*make sure that parameters 'currentPage' and 'itemsOnPage' values are greater.*"))));

        mockMvc.perform(get(apiUrl + "/name")
                .param("namePart", "deoT")
                .param("currentPage", "3")
                .param("itemsOnPage", "3"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new MatchesPattern(Pattern.compile(
                        ".*is greater than total number of available pages.*"))));
    }

    @Test
    public void getPartVideoOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/video", 2)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3));
    }

    @Test
    public void addVideo() throws Exception {
        VideoDto videoDto = VideoDto.builder()
                .author("MyAuthor33")
                .icon("MyIcon33")
                .name("MyNewVideo")
                .url("www.myVideo.ru")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/video", 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(videoDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value("www.myVideo.ru"));

        Videos video = (Videos) entityManager.createQuery("SELECT a from Videos a where a.icon like :icon")
                .setParameter("icon","MyIcon33" )
                .getSingleResult();
        assertEquals("MyAuthor33", video.getAuthor());

        mockMvc.perform(post(apiUrl + "/user/{userId}/video", 500)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(videoDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id 500 is not found"));
    }

    @Test
    public void createVideoAlbum() throws Exception {
        AlbumVideoDto albumVideoDto = AlbumVideoDto.builder()
                .icon("MyNewIconTest")
                .name("MyVideoAlbumTest")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 6000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id 6000 is not found"));

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 6)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyVideoAlbumTest"));

        AlbumVideo album = (AlbumVideo) entityManager.createQuery("SELECT a from AlbumVideo a join fetch a.album a2 where a2.icon like :icon")
                .setParameter("icon", "MyNewIconTest")
                .getSingleResult();
        assertEquals("MyVideoAlbumTest", album.getAlbum().getName());
        assertEquals(com.javamentor.developer.social.platform.models.entity.media.MediaType.VIDEO, album.getAlbum().getMediaType());

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 6)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Video album with name 'MyVideoAlbumTest' already exists"));
    }

    @Test
    public void addInAlbums() throws Exception {
        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "200")
                .param("videoId", "40"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Video id 40 added to album id 200"));

        AlbumVideo albumVideo = (AlbumVideo) entityManager.createQuery("SELECT a from AlbumVideo a join fetch a.videos where a.id = 200")
                .getSingleResult();
        Set<Videos> videoSet = albumVideo.getVideos();
        assertEquals(2,videoSet.size());
        Optional<Videos> video30 = albumVideo.getVideos().stream().filter(a -> a.getId() == 30).findFirst();
        Optional<Videos> video40 = albumVideo.getVideos().stream().filter(a -> a.getId() == 40).findFirst();
        assertTrue(video30.isPresent());
        assertTrue(video40.isPresent());



        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "500")
                .param("videoId", "40"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Video album with id 500 is not found"));

        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "200")
                .param("videoId", "400"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Video with id 400 is not found"));
    }

    @Test
    public void getAllAlbums() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/album", 2)
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2));
    }

    @Test
    public void getFromAlbumOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/album/{albumId}/video", 30)
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));
    }

    @Test
    public void getAlbumVideoOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/video", 3)
                .param("album", "videoAlbum3")
                .param("currentPage", "1")
                .param("itemsOnPage", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));
    }
}