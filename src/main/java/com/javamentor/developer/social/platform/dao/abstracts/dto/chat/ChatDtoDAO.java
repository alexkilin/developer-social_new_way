package com.javamentor.developer.social.platform.dao.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;

import java.util.List;

public interface ChatDtoDAO {
    List<ChatDto> getAllChatDtoByUserId(Long userId);
}
