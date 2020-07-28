package com.javamentor.developer.social.platform.dao.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.ChatDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.chat.Chat;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDAOImpl extends GenericDaoAbstract<Chat, Long> implements ChatDAO {
}
