package com.javamentor.developer.social.platform.restv2.controllers;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.ChatEditTitleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DataSet(value = {
        "datasets/restv2/chat/messages/Messages.yml",
        "datasets/restv2/chat/usersChatTest/Active.yml",
        "datasets/restv2/chat/usersChatTest/Role.yml",
        "datasets/restv2/chat/usersChatTest/User.yml",
        "datasets/restv2/chat/GroupChat.yml",
        "datasets/restv2/chat/GroupChatsMessages.yml",
        "datasets/restv2/chat/SingleChat.yml",
        "datasets/restv2/chat/SingleChatMessages.yml",
        "datasets/restv2/chat/UsersGroupChats.yml"}, cleanBefore = true, cleanAfter = true)
public class ChatControllerV2Tests extends AbstractIntegrationTest {

    private final String apiUrl = "/api/v2/chats";

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @Test
    public void getChatsDto() throws Exception {
        mockMvc.perform(get(apiUrl + "/user/{userId}/chats", 5))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[1].title").value("Group chat #1"));
    }

    @Test
    public void getAllMessageDtoByGroupChatId() throws Exception {
        mockMvc.perform(get(apiUrl + "/group-chats/{chatId}/messages", 10)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(6))
                .andExpect(jsonPath("$.items[0].message").value("Test init message1"));

        mockMvc.perform(get(apiUrl + "/group-chats/{chatId}/messages", 4)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Chat id 4 not found"));
    }

    @Test
    public void getAllMessageDtoBySingleChatId() throws Exception {
        mockMvc.perform(get(apiUrl + "/single-chats/{chatId}/messages", 1)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.items[0].message").value("Test init message1"))
                .andExpect(jsonPath("$.items[1].userSenderImage").value("www.myavatar1.ru/9090"));

        mockMvc.perform(get(apiUrl + "/single-chats/{chatId}/messages", 40)
                .param("currentPage", "1")
                .param("itemsOnPage", "10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Chat id 40 not found"));
    }

    @Test
    public void editGroupChatTitle() throws Exception {
        ChatEditTitleDto chatEditTitleDto = ChatEditTitleDto.builder()
                .title("NewTitle")
                .id(10l)
                .build();

        mockMvc.perform(put(apiUrl + "/group-chats/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chatEditTitleDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("NewTitle"));
    }

    @Test
    public void deleteUserFromSingleChat() throws Exception {
        mockMvc.perform(delete(apiUrl + "/single-chats/{chatId}/user/{userId}", 1, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("done delete chat from user"));

        mockMvc.perform(delete(apiUrl + "/single-chats/{chatId}/user/{userId}", 2, 100))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("user not found"));

        mockMvc.perform(delete(apiUrl + "/single-chats/{chatId}/user/{userId}", 100, 1))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Single chat not found"));

        mockMvc.perform(delete(apiUrl + "/single-chats/{chatId}/user/{userId}", 2, 2))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No such user in chat"));
    }

    @Test
    public void createGroupChat() throws Exception {
        ChatDto chatDto = ChatDto.builder()
                .image("MyImage")
                .title("MyTitle")
                .build();

        /*
        кусок ниже не пашет по причине не возможности передать principal
        */
//        mockMvc.perform(post(apiUrl + "/group-chats")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(chatDto)))
//                .andDo(print())
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("User not found"));

        mockMvc.perform(post(apiUrl + "/group-chats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chatDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}