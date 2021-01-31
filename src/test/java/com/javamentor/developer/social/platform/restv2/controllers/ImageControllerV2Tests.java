package com.javamentor.developer.social.platform.restv2.controllers;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.media.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageCreateDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DataSet(value = {
        "datasets/restv2/image/usersResources/User.yml",
        "datasets/restv2/image/usersResources/Role.yml",
        "datasets/restv2/image/usersResources/Active.yml",
        "datasets/restv2/image/albumTest/Album.yml",
        "datasets/restv2/image/albumTest/AlbumImage.yml",
        "datasets/restv2/image/albumTest/AlbumHasImage.yml",
        "datasets/restv2/image/imageResources/Media.yml" ,
        "datasets/restv2/image/imageResources/Image.yml"
}, strategy = SeedStrategy.REFRESH, cleanAfter = true)
@Sql(value = "/create_user_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@WithUserDetails(userDetailsServiceBeanName = "custom", value = "admin666@user.ru")
public class ImageControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/images";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;

    private final Gson gson = new Gson();


    @Test
    public void createImage() throws Exception {
        ImageCreateDto imageCreateDto = ImageCreateDto.builder()
                .description("MyDescriptionTest")
                .url("www.myURL.ru")
                .userId(200l)
                .build();

        mockMvc.perform(post(apiUrl + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(imageCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value("www.myURL.ru"));

        Image image = (Image) entityManager.createQuery("SELECT a from Image a where a.description like :description")
                .setParameter("description", "MyDescriptionTest")
                .getSingleResult();
        assertEquals("MyDescriptionTest", image.getDescription());
    }

    @Test
    public void deleteImage() throws Exception {
        mockMvc.perform(delete(apiUrl + "/{imageId}", 210))
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/{imageId}", 1000))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 1000 not found"));
    }

    @Test
    public void getImageById() throws Exception {
        mockMvc.perform(get(apiUrl + "/{imageId}", 200))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Image Media Test File 1"));

        mockMvc.perform(get(apiUrl + "/{imageId}", 1000))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 1000 not found"));
    }

    @Test
    public void getAllImagesOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/?userId=200")
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isOk());

        mockMvc.perform(get(apiUrl + "/?userId=1000")
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No user with id 1000 found"));
    }

    @Test
        public void createAlbum() throws Exception {
        AlbumCreateDto albumCreateDto = AlbumCreateDto.builder()
                .icon("MyIconTest")
                .name("MyAlbumNameTest")
                .userId(200l)
                .build();

        mockMvc.perform(post(apiUrl +"/user/{userId}/album", 200)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(albumCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("MyAlbumNameTest"));

        AlbumImage album = (AlbumImage) entityManager.createQuery("SELECT a from AlbumImage a join fetch a.album a2 where a2.icon like :icon")
                .setParameter("icon", "MyIconTest")
                .getSingleResult();
        assertEquals("MyAlbumNameTest", album.getAlbum().getName());
        assertEquals(com.javamentor.developer.social.platform.models.entity.media.MediaType.IMAGE, album.getAlbum().getMediaType());
    }

    @Test
    public void deleteAlbum() throws Exception {
        mockMvc.perform(delete(apiUrl + "/albums/{albumId}", 210))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        mockMvc.perform(delete(apiUrl + "/albums/{albumId}", 1000))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 1000 not found"));

    }

    @Test
    public void addExistImageToAlbum() throws Exception {
        Long albumImageId = 201L;
        AlbumImage albumImage = (AlbumImage) entityManager.createQuery("SELECT a from AlbumImage a join fetch a.images where a.id = :albumImageId")
                .setParameter("albumImageId", albumImageId)
                .getSingleResult();
        Long existImageId = albumImage.getImages().stream().findFirst().get().getId();

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", albumImageId)
                .param("imageId", String.valueOf(existImageId)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format("Image with id %s is already in album with id %s", existImageId, albumImageId)));

    }

    @Test
    public void addImageToAlbum() throws Exception {

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 201)
                .param("imageId", "200"))
                .andExpect(status().isOk())
                .andExpect(content().string("Image id 200 added to album id 201"));

        AlbumImage albumImage = (AlbumImage) entityManager.createQuery("SELECT a from AlbumImage a join fetch a.images where a.id = 201")
                .getSingleResult();
        Set<Image> imageSet = albumImage.getImages();
        assertEquals(4, imageSet.size());

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 1000)
                .param("imageId", "200"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image album with id 1000 is not found"));

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 200)
                .param("imageId", "1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 1000 is not found"));
    }

    @Test
    public void removeImageFromAlbum() throws Exception {
        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 201)
                .param("id", "203"))
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 201)
                .param("id", "1000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 1000 not found"));


        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 1000)
                .param("id", "201"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 1000 not found"));
    }

    @Test
    public void getImagesFromAlbumById() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums/{albumId}/images", 200)
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(3));

        mockMvc.perform(get(apiUrl + "/albums/{albumId}/images", 1000)
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 1000 not found"));
    }

    @Test
    public void getImageAlbumById() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums/{albumId}", 200))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("imageAlbum"));

        mockMvc.perform(get(apiUrl + "/albums/{albumId}", 1000))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 1000 not found"));
    }

    @Test
    public void getAllImageAlbumsOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums")
                .param("userId", "200")
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isOk());

        mockMvc.perform(get(apiUrl + "/albums")
                .param("userId", "1000")
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No user with id 1000 found"));
    }
}