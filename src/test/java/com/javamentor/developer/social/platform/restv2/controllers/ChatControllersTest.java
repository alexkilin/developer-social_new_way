package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.ChatEditTitleDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DataSet(value = {"datasets/chat/usersChatTest/Active.yml",
        "datasets/chat/usersChatTest/User.yml",
        "datasets/chat/usersChatTest/Role.yml",
        "datasets/chat/messages/Messages.yml",
        "datasets/chat/GroupChat.yml",
        "datasets/chat/GroupChatsMessages.yml",
        "datasets/chat/SingleChat.yml",
        "datasets/chat/SingleChatMessages.yml",
        "datasets/chat/UsersGroupChats.yml",}, cleanBefore = true, cleanAfter = true)
public class ChatControllersTest extends AbstractIntegrationTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new Gson();

    @Test
    @DataSet(value = {"datasets/chat/usersChatTest/Active.yml",
            "datasets/chat/usersChatTest/User.yml",
            "datasets/chat/usersChatTest/Role.yml",}, cleanBefore = true, cleanAfter = true)
    void createGroupChat() throws Exception {

        ChatDto chatDto = ChatDto.builder()
                .image("image")
                .lastMessage("lastik")
                .title("Tit")
                .type("groupChats")
                .active("ACTIVE")
                .build();

        String json = new Gson().toJson(chatDto);

        this.mockMvc.perform(post( "/api/user/chat/group/create")
        .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
    @Test
    void createGroupChatWithId() throws Exception {

        ChatDto chatDto = ChatDto.builder()
                .id(2L)
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
                .andExpect(status().isBadRequest());
    }
    @Test
    void createGroupChatWithoutImage() throws Exception {

        ChatDto chatDto = ChatDto.builder()
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
                .andExpect(status().isBadRequest());
    }
    @Test
    void createGroupChatWithoutTitle() throws Exception {

        ChatDto chatDto = ChatDto.builder()
                .image("image")
                .lastMessage("lastik")
                .type("groupChats")
                .active("1")
                .build();

        String json = new Gson().toJson(chatDto);

        this.mockMvc.perform(post( "/api/user/chat/group/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getChats() throws Exception {

        this.mockMvc.perform(get("/api/user/chats"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));

    }

    @Test
    public void deleteUserFromSingleChat() throws Exception {

        this.mockMvc.perform(post("/api/user/1/user/2/delete"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("done delete chat from user"));
    }

    @Test
    public void getAllMessageByGroupChatId() throws Exception {

        this.mockMvc.perform(get("/api/user/groupChats/{chatId}/messages", 1))
                .andDo(print())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$[0].lastRedactionDate").value("2020-09-02T06:36:50.977"))
                .andExpect(jsonPath("$[0].persistDate").value("2020-09-02T06:36:50.977"))
                .andExpect(jsonPath("$[0].userSenderImage").value("www.myavatar0.ru/9090"))
                .andExpect(jsonPath("$[0].message").value("Test init message1"));

    }

    @Test
    public void getAllMessageBySingleChatId() throws Exception {

        this.mockMvc.perform(get("/api/user/singleChats/{chatId}/messages", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void editGroupChatTitle() throws Exception {

        ChatEditTitleDto chatEditTitleDto = ChatEditTitleDto.builder()
                .id(1L)
                .title("New_Title").build();

        this.mockMvc.perform(put("/api/user/chat/group/edit/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chatEditTitleDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("title").value("New_Title"));
    }
}
