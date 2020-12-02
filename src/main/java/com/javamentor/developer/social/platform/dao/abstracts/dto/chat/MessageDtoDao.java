package com.javamentor.developer.social.platform.dao.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.dto.chat.MediaDto;

import java.util.List;

public interface MessageDtoDao {
    List<MediaDto> getAllMediasOfMessage(List<Long> messageId);
}
