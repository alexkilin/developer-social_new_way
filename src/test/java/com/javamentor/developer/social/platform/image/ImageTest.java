package com.javamentor.developer.social.platform.image;

import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ImageTest extends AbstractIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllImagesByUserId() throws Exception {
        this.mockMvc.perform(get("/api/image/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
