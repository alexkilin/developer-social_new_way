package com.javamentor.developer.social.platform.service.abstracts.model.chat;

import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface GroupChatService extends GenericService<GroupChat, Long> {

    Optional<GroupChat> getByIdWithChat(Long id);
}
