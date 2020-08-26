package com.javamentor.developer.social.platform.example;

import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {"testdata/chat.yml"}, cleanBefore = true, cleanAfter = true)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getChatById() throws Exception {
        this.mockMvc.perform(get("/api/test/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("MyChat"))
                .andExpect(jsonPath("$.persistDate").value("2020-01-01 13:58:56"));
    }
}