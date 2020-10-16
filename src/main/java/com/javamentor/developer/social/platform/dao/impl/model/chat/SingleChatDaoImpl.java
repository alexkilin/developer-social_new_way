package com.javamentor.developer.social.platform.dao.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.SingleChatDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import org.springframework.stereotype.Repository;

@Repository
public class SingleChatDaoImpl extends GenericDaoAbstract<SingleChat,Long> implements SingleChatDao {
}
