package com.javamentor.developer.social.platform.dao.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.MediaDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;

import java.util.List;

public interface MessageDtoDao {
    List<MediaDto> getAllMediasOfMessage(List<Long> messageId);
}
