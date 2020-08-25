package com.javamentor.developer.social.platform.dao.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.GroupChatDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import org.springframework.stereotype.Repository;

@Repository
public class GroupChatDAOImpl extends GenericDaoAbstract<GroupChat, Long> implements GroupChatDAO {
}
