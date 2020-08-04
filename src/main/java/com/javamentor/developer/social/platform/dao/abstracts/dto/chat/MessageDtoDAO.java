package com.javamentor.developer.social.platform.dao.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;

import java.util.List;

public interface MessageDtoDAO {
    List<MessageDto> getAllMessageDtoByChatId(Long chatId);
}
