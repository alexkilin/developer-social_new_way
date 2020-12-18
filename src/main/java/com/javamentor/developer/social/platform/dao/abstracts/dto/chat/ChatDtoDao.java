package com.javamentor.developer.social.platform.dao.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;

import java.util.List;

public interface ChatDtoDao {
    List<ChatDto> getAllChatDtoByUserId(Long userId);

    ChatDto getChatDtoByGroupChatId(Long chatId);

    List<ChatDto> getChatDtoByChatName(Long userId,String search);
}
