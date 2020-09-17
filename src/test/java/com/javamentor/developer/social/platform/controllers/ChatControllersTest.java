package com.javamentor.developer.social.platform.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DataSet(value = {
        "datasets/postcommentset/user/Active.yml",
        "datasets/postcommentset/user/Role.yml",
        "datasets/postcommentset/user/Status.yml",
        "datasets/postcommentset/user/User.yml"
}, cleanBefore = true, cleanAfter = true)
public class ChatControllersTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createGroupChat() throws Exception {

        ChatDto chatDto = ChatDto.builder()
                .image("image")
                .lastMessage("lastik")
                .title("Tit")
                .type("groupChats")
                .active("1")
                .build();

        String json = new Gson().toJson(chatDto);


        this.mockMvc.perform(post( "/api/user/chat/group/create")
        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
}
