package com.javamentor.developer.social.platform.service.abstracts.model.chat;

import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface SingleChatService extends GenericService<SingleChat,Long> {
    boolean deleteUserFromSingleChat(SingleChat singleChat, Long userId);
}
