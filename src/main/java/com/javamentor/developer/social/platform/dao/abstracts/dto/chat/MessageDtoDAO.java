package com.javamentor.developer.social.platform.dao.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.MessageChatDto;

import java.util.List;

public interface MessageDtoDAO {
    List<MessageChatDto> getAllMessageDtoByChatId(Long chatId);
}
