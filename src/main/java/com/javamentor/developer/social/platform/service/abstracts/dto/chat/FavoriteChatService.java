package com.javamentor.developer.social.platform.service.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface FavoriteChatService extends GenericService<FavoriteChat, Long> {

    Optional<FavoriteChat> getFavoriteByChatIdAndUserId(Long chatId, Long userId);
}
