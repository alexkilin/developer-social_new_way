package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.media.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageCreateDto;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {
        "datasets/restv2/image/usersResources/User.yml",
        "datasets/restv2/image/usersResources/Role.yml",
        "datasets/restv2/image/albumTest/Album.yml",
        "datasets/restv2/image/albumTest/AlbumImage.yml",
        "datasets/restv2/image/albumTest/AlbumHasImage.yml",
        "datasets/restv2/image/albumTest/Active.yml",
        "datasets/restv2/image/Media.yml",
        "datasets/restv2/image/image.yml"}, cleanBefore = true, cleanAfter = true)
public class ImageControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/images";

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();


    @Test
    public void createImage() throws Exception {
        ImageCreateDto imageCreateDto = ImageCreateDto.builder()
                .description("MyDescription")
                .url("www.myURL.ru")
                .userId(6l)
                .build();

        mockMvc.perform(post(apiUrl + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(imageCreateDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value("www.myURL.ru"));
    }

    @Test
    public void deleteImage() throws Exception {
        mockMvc.perform(delete(apiUrl + "/{imageId}", 140))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/{imageId}", 5000))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 5000 not found"));
    }

    @Test
    public void getImageById() throws Exception {
        mockMvc.perform(get(apiUrl + "/{imageId}", 950))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Image Media Test File 94"));

        mockMvc.perform(get(apiUrl + "/{imageId}", 9000))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 9000 not found"));
    }

    @Test
    public void getAllImagesOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/?userId=3")
                .param("offset", "0")
                .param("limit", "50"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(apiUrl + "/?userId=500")
                .param("offset", "0")
                .param("limit", "50"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("No user with id 500 found"));
    }

    @Test
    public void createAlbum() throws Exception {
        AlbumCreateDto albumCreateDto = AlbumCreateDto.builder()
                .icon("MyIcon")
                .name("MyAlbumName")
                .userId(5l)
                .build();

        mockMvc.perform(post(apiUrl + "/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumCreateDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("MyAlbumName"));
    }

    @Test
    public void deleteAlbum() throws Exception {
        mockMvc.perform(delete(apiUrl + "/albums/{albumId}", 26))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        mockMvc.perform(delete(apiUrl + "/albums/{albumId}", 260))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 260 not found"));

    }

    @Test
    public void addImageToAlbum() throws Exception {
        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 32)
                .param("id", "30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Image 30 added to album 32"));

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 32000)
                .param("id", "30"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 32000 not found"));

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 32)
                .param("id", "30000"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 30000 not found"));
    }

    @Test
    public void removeImageFromAlbum() throws Exception {
        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 27)
                .param("id", "30"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 27)
                .param("id", "3400"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 3400 not found"));


        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 2700)
                .param("id", "30"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 2700 not found"));
    }

    @Test
    public void getImagesFromAlbumById() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums/{albumId}/images", 23)
                .param("offset", "0")
                .param("limit", "50"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        mockMvc.perform(get(apiUrl + "/albums/{albumId}/images", 2300)
                .param("offset", "0")
                .param("limit", "50"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 2300 not found"));
    }

    @Test
    public void getImageAlbumById() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums/{albumId}", 30))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("imageAlbum"));

        mockMvc.perform(get(apiUrl + "/albums/{albumId}", 2200))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 2200 not found"));
    }

    @Test
    public void getAllImageAlbumsOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums")
                .param("userId", "2")
                .param("offset", "0")
                .param("limit", "50"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(apiUrl + "/albums")
                .param("userId", "200")
                .param("offset", "0")
                .param("limit", "50"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("No user with id 200 found"));
    }
}