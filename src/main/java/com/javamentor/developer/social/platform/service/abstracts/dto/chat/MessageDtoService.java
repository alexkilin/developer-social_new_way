package com.javamentor.developer.social.platform.service.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.MessageChatDto;

import java.util.List;

public interface MessageDtoService {
    List<MessageChatDto> getAllMessageDtoByChatId(Long chatId);
}
