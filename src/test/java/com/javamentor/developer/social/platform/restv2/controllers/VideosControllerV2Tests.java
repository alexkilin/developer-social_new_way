package com.javamentor.developer.social.platform.restv2.controllers;


import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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
        "datasets/restv2/video/Video.yml"}, cleanBefore = true, cleanAfter = true)
class VideosControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/video";

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @Test
    public void getPartVideos() throws Exception {
        mockMvc.perform(get(apiUrl + "/")
                .param("currentPage", "0")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").value(10))
                .andExpect(jsonPath("$[0].url").value("www.myvideo1.ru"))
                .andExpect(jsonPath("$[0].icon").value("TestIcon0"))
                .andExpect(jsonPath("$[0].name").value("VideoTestName 0"))
                .andExpect(jsonPath("$[0].author").value("TestAuthor 0"))
                .andExpect(jsonPath("$[1].id").value(20))
                .andExpect(jsonPath("$[2].id").value(40))
                .andExpect(jsonPath("$[3].id").value(50));
    }

    @Test
    public void getVideoOfName() throws Exception {
        mockMvc.perform(get(apiUrl + "/name?name=VideoTestName 4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(50));
    }

    @Test
    public void getPartVideoOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/video", 2)
                .param("currentPage", "0")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void addVideo() throws Exception {
        VideoDto videoDto = VideoDto.builder()
                .author("MyAuthor")
                .icon("MyIcon")
                .name("MyNewVideo")
                .url("www.myVideo.ru")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/video", 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(videoDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value("www.myVideo.ru"));

        mockMvc.perform(post(apiUrl + "/user/{userId}/video", 500)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(videoDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Пользователь с 500 id не найден"));
    }

    @Test
    public void createVideoAlbum() throws Exception {
        AlbumVideoDto albumVideoDto = AlbumVideoDto.builder()
                .icon("MyNewIcon")
                .name("MyVideoAlbum")
                .build();

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 6)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyVideoAlbum"));

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 600)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Пользователь с %d id не найден"));

        mockMvc.perform(post(apiUrl + "/user/{userId}/album", 6)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumVideoDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Video album with name 'MyVideoAlbum' already exists"));
    }

    @Test
    public void addInAlbums() throws Exception {
        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "20")
                .param("videoId", "40"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Video id 40 added to album id 20"));

        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "500")
                .param("videoId", "40"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Видеоальбом с id  500 не найден"));

        mockMvc.perform(put(apiUrl + "/album/video")
                .param("albumId", "20")
                .param("videoId", "400"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Видео с id  400 не найдено"));
    }

    @Test
    public void getAllAlbums() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/album", 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void getFromAlbumOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/album/{albumId}/video", 30))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void getAlbumVideoOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/video", 3)
                .param("album", "videoAlbum3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}