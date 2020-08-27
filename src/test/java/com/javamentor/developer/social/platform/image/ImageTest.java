package com.javamentor.developer.social.platform.image;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*@DataSet(value = {

        "image/Active.yml",
        "image/Role.yml",
        "image/Status.yml",
        "image/User.yml",
        "image/Album.yml",
        "image/AlbumImage.yml",
        "image/Media.yml",
        "image/Image.yml"

}, cleanBefore = true)*/

public class ImageTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

   @DataSet(value = {

            "image/Active.yml",
            "image/Role.yml",
            "image/Status.yml",
            "image/User.yml",
            "image/Album.yml",
            "image/AlbumImage.yml",
            "image/Media.yml",
            "image/Image.yml"

    }, cleanBefore = true)
    @Test
    public void addImage() throws Exception {

        this.mockMvc.perform(post("/api/image/create" +
                "?userId=1" +
                "&url=www.testImg.ru" +
                "&albumId=1" +
                "&description=lalalay"))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/image/all?id=1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    /*@DataSet(value = {

            "image/Active.yml",
            "image/Role.yml",
            "image/Status.yml",
            "image/User.yml",
            "image/Album.yml",
            "image/AlbumImage.yml",
            "image/Media.yml",
            "image/Image.yml"

    }, cleanBefore = true, cleanAfter = true)*/
    @Test
    public void getAllImagesByUserId() throws Exception {

        this.mockMvc.perform(get("/api/image/all?id=1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

   /*    @Test
    public void deleteImageById() throws Exception {

        this.mockMvc.perform(get("/api/image/del?id=1"))
                .andDo(print())
                .andExpect(status().isOk());

     this.mockMvc.perform(get("/api/image/all?id=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }*/


}
