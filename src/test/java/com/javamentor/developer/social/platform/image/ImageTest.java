package com.javamentor.developer.social.platform.image;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DataSet(value = {
        "image/Role.yml",
        "image/Album.yml",
        "image/Status.yml",
        "image/User.yml",
        "image/Media.yml",
        "image/Image.yml",
        "image/Album.yml",
        "image/AlbumImage.yml",
        }, cleanBefore = true)

public class ImageTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllImagesByUserId() throws Exception {
        this.mockMvc.perform(get("/api/image/all?id=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
