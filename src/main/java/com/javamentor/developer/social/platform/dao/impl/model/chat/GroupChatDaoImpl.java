package com.javamentor.developer.social.platform.dao.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.GroupChatDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GroupChatDaoImpl extends GenericDaoAbstract<GroupChat, Long> implements GroupChatDao {
    @Override
    public Optional<GroupChat> getByIdWithChat(Long id) {
        return SingleResultUtil.getSingleResultOrNull(
                entityManager.createQuery(
                        "SELECT gc " +
                                "FROM GroupChat AS gc " +
                                "JOIN FETCH gc.chat " +
                                "WHERE gc.id = :idParam", GroupChat.class
                ).setParameter("idParam", id)
        );
    }
}
