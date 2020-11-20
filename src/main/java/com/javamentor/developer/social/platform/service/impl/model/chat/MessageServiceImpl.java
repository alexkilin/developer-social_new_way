package com.javamentor.developer.social.platform.service.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.MessageDao;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.MessageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends GenericServiceAbstract<Message, Long> implements MessageService {

    public MessageServiceImpl(MessageDao dao) {
        super(dao);
    }
}
