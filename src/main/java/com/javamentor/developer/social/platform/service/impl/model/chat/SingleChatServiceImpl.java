package com.javamentor.developer.social.platform.service.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.SingleChatDAO;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleChatServiceImpl extends GenericServiceAbstract<SingleChat,Long> implements SingleChatService {

    @Autowired
    public SingleChatServiceImpl(SingleChatDAO singleChatDAO) {
        super(singleChatDAO);
    }
}
