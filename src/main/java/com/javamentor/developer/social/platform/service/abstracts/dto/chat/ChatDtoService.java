package com.javamentor.developer.social.platform.service.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.List;
import java.util.Map;

public interface ChatDtoService {

    List<ChatDto> getAllChatDtoByUserId(Long userId);

    ChatDto getChatDtoByGroupChatId(Long chatId);

    PageDto<ChatDto, Object> getChatDtoByChatName(Map<String, Object> parameters);

    List<ChatDto> getAllFavoriteChatDto(Long userId);
}
