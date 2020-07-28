package com.javamentor.developer.social.platform.dao.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.MessageDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDAOImpl extends GenericDaoAbstract<Message, Long> implements MessageDAO {
}
