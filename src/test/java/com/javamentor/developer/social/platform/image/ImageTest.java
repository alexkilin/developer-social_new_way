package com.javamentor.developer.social.platform.image;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import com.javamentor.developer.social.platform.models.dto.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.ImageCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {
        "datasets/image/Active.yml",
        "datasets/image/User.yml",
        "datasets/image/Role.yml",
        "datasets/image/Media.yml",
        "datasets/image/Image.yml",
        "datasets/image/Album.yml",
        "datasets/image/AlbumImage.yml"}, cleanBefore = true, cleanAfter = true)
public class ImageTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @Test
    @DataSet(value = {
            "datasets/image/Active.yml",
            "datasets/image/User.yml",
            "datasets/image/Role.yml",
    }, cleanBefore = true, cleanAfter = true)
    public void createImage() throws Exception {
        ImageCreateDto imageDto = ImageCreateDto.builder()
                .userId(60L)
                .description("cat")
                .url("/images/cat35.jpg")
                .build();

        this.mockMvc.perform(post("/api/images/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(imageDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNumber());

        imageDto = ImageCreateDto.builder()
                .description("cat")
                .url("/images/cat35.jpg")
                .build();

        this.mockMvc.perform(post("/api/images/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(imageDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User Id cant be null"));
    }

    @Test
    public void deleteImage() throws Exception {
        this.mockMvc.perform(delete("/api/images/2"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/api/images/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getImageById() throws Exception {
        this.mockMvc.perform(get("/api/images/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("url").value("www.myimage.ru"))
                .andExpect(jsonPath("description").value("Image Media Test File 1"))
                .andExpect(jsonPath("persistDateTime").exists());

        this.mockMvc.perform(get("/api/images/222222222"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllImagesOfUserByUserId() throws Exception {
        this.mockMvc.perform(get("/api/images/")
                .queryParam("userId", "60")
                .queryParam("offset", "0")
                .queryParam("limit", "3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value("92"))
                .andExpect(jsonPath("$[1].id").value("93"))
                .andExpect(jsonPath("$[2].id").value("95"));
    }

    @Test
    @DataSet(value = {
            "datasets/image/Active.yml",
            "datasets/image/User.yml",
            "datasets/image/Role.yml",
    }, cleanBefore = true, cleanAfter = true)
    public void createAlbum() throws Exception {
        AlbumCreateDto albumDto = AlbumCreateDto.builder()
                .userId(60L)
                .icon("album icon")
                .name("test image album 33")
                .build();

        this.mockMvc.perform(post("/api/images/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").isNumber());

        albumDto = AlbumCreateDto.builder()
                .icon("album icon")
                .name("test image album 33")
                .build();

        this.mockMvc.perform(post("/api/images/albums")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User Id cant be null"));
    }

    @Test
    public void deleteAlbum() throws Exception {
        this.mockMvc.perform(delete("/api/images/albums/21"))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(delete("/api/images/albums/21"))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(delete("/api/images/albums/20"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void addImageToAlbum() throws Exception {
        this.mockMvc.perform(put("/api/images/albums/21/images")
                .queryParam("id", "8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Image 8 added to album 21"));

        this.mockMvc.perform(put("/api/images/albums/2111/images")
                .queryParam("id", "8"))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(put("/api/images/albums/21/images")
                .queryParam("id", "888888"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteImageFromAlbum() throws Exception {
        this.mockMvc.perform(delete("/api/images/albums/21/images")
                .queryParam("id", "8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Image 8 removed from album 21"));

        this.mockMvc.perform(delete("/api/images/albums/2111/images")
                .queryParam("id", "8"))
                .andDo(print())
                .andExpect(status().isNotFound());

        this.mockMvc.perform(delete("/api/images/albums/21/images")
                .queryParam("id", "888888"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getImagesFromAlbumByAlbumId() throws Exception {
        this.mockMvc.perform(get("/api/images/albums/66/images")
                .queryParam("offset", "0")
                .queryParam("limit", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value("4"))
        .andExpect(jsonPath("$[0].id").value("2"))
                .andExpect(jsonPath("$[0].url").value("www.myimage.ru"))
                .andExpect(jsonPath("$[0].description").value("Image Media Test File 1"))
                .andExpect(jsonPath("$[0].persistDateTime").value("2020-09-28T14:25:35.605689"))
                .andExpect(jsonPath("$[1].id").value("3"))
                .andExpect(jsonPath("$[2].id").value("5"))
                .andExpect(jsonPath("$[3].id").value("8"));

        this.mockMvc.perform(get("/api/images/albums/666666666666/images")
                .queryParam("offset", "0")
                .queryParam("limit", "3"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAlbumById() throws Exception {
        this.mockMvc.perform(get("/api/images/albums/66"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("66"))
                .andExpect(jsonPath("name").value("imageAlbum"))
                .andExpect(jsonPath("icon").value("random icon"));

        this.mockMvc.perform(get("/api/images/albums/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllAlbumsByUserId() throws Exception {
        this.mockMvc.perform(get("/api/images/albums")
                .queryParam("userId", "60")
                .queryParam("offset", "0")
                .queryParam("limit", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value("5"));

        this.mockMvc.perform(get("/api/images/albums")
                .queryParam("userId", "65")
                .queryParam("offset", "0")
                .queryParam("limit", "10"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
