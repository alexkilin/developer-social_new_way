package com.javamentor.developer.social.platform.service.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.FavoriteChatDao;
import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.FavoriteChatService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteChatServiceImpl extends GenericServiceAbstract<FavoriteChat, Long> implements FavoriteChatService {

    private final FavoriteChatDao dao;

    @Autowired
    public FavoriteChatServiceImpl(FavoriteChatDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Override
    public void addChatTOFavorites(FavoriteChat favoriteChat) {
        dao.create(favoriteChat);
    }
}