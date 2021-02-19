package com.javamentor.developer.social.platform.dao.abstracts.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;

import java.util.Optional;

public interface FavoriteChatDao extends GenericDao<FavoriteChat, Long> {

    public Optional<FavoriteChat> getFavoriteChatByChatIdAndUserId(Long chatId, Long userId);
}
