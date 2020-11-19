package com.javamentor.developer.social.platform.service.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.GroupChatDao;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class GroupChatServiceImpl extends GenericServiceAbstract<GroupChat, Long> implements GroupChatService {

    public GroupChatServiceImpl(GroupChatDao dao) {
        super(dao);
    }

}
