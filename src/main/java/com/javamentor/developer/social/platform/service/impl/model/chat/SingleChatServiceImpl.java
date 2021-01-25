package com.javamentor.developer.social.platform.service.impl.model.chat;

import com.javamentor.developer.social.platform.dao.abstracts.model.chat.SingleChatDao;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SingleChatServiceImpl extends GenericServiceAbstract<SingleChat,Long> implements SingleChatService {

    private final SingleChatDao singleChatDAO;

    @Autowired
    public SingleChatServiceImpl(SingleChatDao singleChatDAO) {
        super(singleChatDAO);
        this.singleChatDAO = singleChatDAO;
    }

    @Override
    @Transactional
    public boolean deleteUserFromSingleChat(SingleChat singleChat, Long userId) {
        if (singleChat.getUserOne().getUserId().equals(userId)){
            singleChat.setDeletedForUserOne(true);
            singleChatDAO.update(singleChat);
            return true;
        } else if (singleChat.getUserTwo().getUserId().equals(userId)){
            singleChat.setDeletedForUserTwo(true);
            singleChatDAO.update(singleChat);
            return true;
        }
        return false;
    }

}
