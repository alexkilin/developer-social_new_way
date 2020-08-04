package com.javamentor.developer.social.platform.service.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;

import java.util.List;

public interface ChatDtoService {
    List<ChatDto> getAllChatDtoByUserId(Long userId);
}
