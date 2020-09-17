package com.javamentor.developer.social.platform.chat;

import com.github.database.rider.core.api.dataset.DataSet;
import com.javamentor.developer.social.platform.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Chat extends AbstractIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @DataSet(value = {
//            "chat/user/Active.yml",
//            "chat/user/Role.yml",
//            "chat/user/Status.yml",
//            "chat/user/User.yml",
//            "chat/chats/SingleChat.yml",
//            "chat/chats/User_single_chat.yml",
//    }, cleanBefore = true) /*, cleanAfter = true*/
//    @Test
//    public void testDeleteUserFromChat() throws Exception {
//        this.mockMvc.perform(post("/api/user/1/user/1/delete"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

}
