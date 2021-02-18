package com.javamentor.developer.social.platform.service.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.GroupChatDao;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupChatServiceImpl extends GenericServiceAbstract<GroupChat, Long> implements GroupChatService {

    private final GroupChatDao dao;

    @Autowired
    public GroupChatServiceImpl(GroupChatDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Override
    public Optional<GroupChat> getByIdWithChat(Long id) {
        return dao.getByIdWithChat(id);
    }
}
