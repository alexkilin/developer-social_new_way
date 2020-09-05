package com.javamentor.developer.social.platform.service.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.SingleChatDAO;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SingleChatServiceImpl extends GenericServiceAbstract<SingleChat,Long> implements SingleChatService {

    private final SingleChatDAO singleChatDAO;

    @Autowired
    public SingleChatServiceImpl(SingleChatDAO singleChatDAO) {
        super(singleChatDAO);
        this.singleChatDAO = singleChatDAO;
    }

    @Transactional
    @Override
    public boolean deleteUserFromSingleChat(SingleChat singleChat, Long userId) {
        if (singleChat.getUserOne().getUserId().equals(userId)){
            singleChat.setUserOne(null);
            singleChatDAO.update(singleChat);
            return true;
        } else if (singleChat.getUserTwo().getUserId().equals(userId)){
            singleChat.setUserTwo(null);
            singleChatDAO.update(singleChat);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUserFromSingleChat(SingleChat singleChat, Long userId) {
        if (singleChat.getUserOne().getUserId().equals(userId)){
            singleChat.setUserOne(null);
            return true;
        } else if (singleChat.getUserTwo().getUserId().equals(userId)){
            singleChat.setUserTwo(null);
            return true;
        }
        return false;
    }
}
