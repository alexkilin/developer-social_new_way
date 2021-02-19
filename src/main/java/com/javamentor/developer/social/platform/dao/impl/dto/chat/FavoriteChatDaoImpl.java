package com.javamentor.developer.social.platform.dao.impl.dto.chat;

import com.javamentor.developer.social.platform.dao.abstracts.dto.chat.FavoriteChatDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class FavoriteChatDaoImpl extends GenericDaoAbstract<FavoriteChat, Long> implements FavoriteChatDao {

    @Override
    public Optional<FavoriteChat> getFavoriteChatByChatIdAndUserId(Long chatId, Long userId) {
            TypedQuery<FavoriteChat> query = entityManager.createQuery(
                    "SELECT f FROM FavoriteChat f " +
                            "WHERE  f.chat.id = :chatId " +
                            "AND f.user.userId = :userId", FavoriteChat.class)
                    .setParameter("chatId", chatId)
                    .setParameter("userId", userId);
            return SingleResultUtil.getSingleResultOrNull(query);
    }
}
