package com.javamentor.developer.social.platform.dao.abstracts.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;

import java.util.Optional;

public interface GroupChatDao extends GenericDao<GroupChat, Long> {
    Optional<GroupChat> getByIdWithChat(Long id);
}
