package com.javamentor.developer.social.platform.service.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.List;
import java.util.Map;

public interface MessageDtoService {

    PageDto<MessageDto, ?> getAllMessageDtoFromGroupChatByChatId(Map<String, Object> parameters);

    PageDto<MessageDto, ?> getAllMessageDtoFromSingleChatByChatId(Map<String, Object> parameters);
}
