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
//    @Test
//    public void test() throws Exception{
//        System.out.println("hello");
//    }


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
                .userId(6l)
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
        mockMvc.perform(delete(apiUrl + "/{imageId}", 140))
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/{imageId}", 5000))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 5000 not found"));
    }

    @Test
    public void getImageById() throws Exception {
        mockMvc.perform(get(apiUrl + "/{imageId}", 950))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Image Media Test File 94"));

        mockMvc.perform(get(apiUrl + "/{imageId}", 9000))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 9000 not found"));
    }

    @Test
    public void getAllImagesOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/?userId=3")
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isOk());

        mockMvc.perform(get(apiUrl + "/?userId=500")
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No user with id 500 found"));
    }

    @Test
        public void createAlbum() throws Exception {
        AlbumCreateDto albumCreateDto = AlbumCreateDto.builder()
                .icon("MyIconTest")
                .name("MyAlbumNameTest")
                .userId(5l)
                .build();

        mockMvc.perform(post(apiUrl +"/user/{userId}/album", 2)
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
        mockMvc.perform(delete(apiUrl + "/albums/{albumId}", 26))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));

        mockMvc.perform(delete(apiUrl + "/albums/{albumId}", 260))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 260 not found"));

    }

    @Test
    public void addImageToAlbum() throws Exception {
        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 27)
                .param("imageId", "2000"))
                .andExpect(status().isOk())
                .andExpect(content().string("Image id 2000 added to album id 27"));

        AlbumImage albumImage = (AlbumImage) entityManager.createQuery("SELECT a from AlbumImage a join fetch a.images where a.id = 27")
                .getSingleResult();
        Set<Image> imageSet = albumImage.getImages();
        assertEquals(2, imageSet.size());

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 32000)
                .param("imageId", "30"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image album with id 32000 is not found"));

        mockMvc.perform(put(apiUrl + "/albums/{albumId}/images", 32)
                .param("imageId", "30000"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 30000 is not found"));
    }

    @Test
    public void removeImageFromAlbum() throws Exception {
        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 27)
                .param("id", "30"))
                .andExpect(status().isOk());

        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 27)
                .param("id", "3400"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Image with id 3400 not found"));


        mockMvc.perform(delete(apiUrl + "/albums/{albumId}/images", 2700)
                .param("id", "30"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 2700 not found"));
    }

    @Test
    public void getImagesFromAlbumById() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums/{albumId}/images", 23)
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1));

        mockMvc.perform(get(apiUrl + "/albums/{albumId}/images", 2300)
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 2300 not found"));
    }

    @Test
    public void getImageAlbumById() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums/{albumId}", 30))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("imageAlbum"));

        mockMvc.perform(get(apiUrl + "/albums/{albumId}", 2200))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Album with id 2200 not found"));
    }

    @Test
    public void getAllImageAlbumsOfUser() throws Exception {
        mockMvc.perform(get(apiUrl + "/albums")
                .param("userId", "2")
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isOk());

        mockMvc.perform(get(apiUrl + "/albums")
                .param("userId", "200")
                .param("currentPage", "1")
                .param("itemsOnPage", "50"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No user with id 200 found"));
    }
}