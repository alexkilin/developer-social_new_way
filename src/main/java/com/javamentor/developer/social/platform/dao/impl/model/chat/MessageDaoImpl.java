package com.javamentor.developer.social.platform.dao.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.MessageDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoImpl extends GenericDaoAbstract<Message, Long> implements MessageDao {
}
