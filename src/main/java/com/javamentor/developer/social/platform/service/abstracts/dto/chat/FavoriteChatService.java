package com.javamentor.developer.social.platform.service.abstracts.dto.chat;

import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface FavoriteChatService extends GenericService<FavoriteChat, Long> {
    void addChatTOFavorites(FavoriteChat favoriteChat);
}
