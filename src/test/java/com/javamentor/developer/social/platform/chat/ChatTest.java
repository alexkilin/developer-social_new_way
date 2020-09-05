package com.javamentor.developer.social.platform.chat;

import com.github.database.rider.core.api.dataset.DataSet;
import com.google.gson.Gson;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import com.javamentor.developer.social.platform.models.dto.chat.ChatEditTitleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DataSet(value = {"datasets/chat/usersChatTest/Active.yml",
        "datasets/chat/usersChatTest/User.yml",
        "datasets/chat/usersChatTest/Role.yml",
        "datasets/chat/usersChatTest/Status.yml",
        "datasets/chat/messages/Messages.yml",
        "datasets/chat/GroupChat.yml",
        "datasets/chat/GroupChatsMessages.yml",
        "datasets/chat/SingleChat.yml",
        "datasets/chat/SingleChatMessages.yml",
        "datasets/chat/UsersGroupChats.yml",
        "datasets/chat/UserSingleChat.yml"}, cleanBefore = true, cleanAfter = true)
class ChatTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Gson gson;

    @Test
    void getAllChatsOfCurrentUser() throws Exception {
        this.mockMvc.perform(get("/api/user/chats"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void getAllSingleChatMessagesOfCurrentUser() throws Exception {
        this.mockMvc.perform(get("/api/user/singleChats/3/messages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getAllGroupChatMessagesOfCurrentUser() throws Exception {
        this.mockMvc.perform(get("/api/user/groupChats/2/messages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6));
    }

    @Test
    void editGroupChatTitle() throws Exception {
        Long chatId = 2L;

        ChatEditTitleDto chatEditTitleDto = ChatEditTitleDto.builder()
                .id(chatId)
                .title("New title for group chat")
                .build();

        this.mockMvc.perform(put("/api/user/chat/group/edit/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chatEditTitleDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(chatId))
                .andExpect(jsonPath("title").value("New title for group chat"));
    }

    @Test
    void editGroupChatTitleWithInvalidChatId() throws Exception {
        Long chatId = 2091L;

        ChatEditTitleDto chatEditTitleDto = ChatEditTitleDto.builder()
                .id(chatId)
                .title("New title for group chat")
                .build();

        this.mockMvc.perform(put("/api/user/chat/group/edit/title")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(chatEditTitleDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUserFromChat() throws Exception {
        this.mockMvc.perform(post("/api/chat/1/user/1/delete"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
