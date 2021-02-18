package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.FavoriteChatDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteChatDaoImpl extends GenericDaoAbstract<FavoriteChat, Long> implements FavoriteChatDao {
}
