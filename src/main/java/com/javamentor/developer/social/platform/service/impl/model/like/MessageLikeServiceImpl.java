package com.javamentor.developer.social.platform.service.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.MessageLikeDao;
import com.javamentor.developer.social.platform.models.entity.like.MessageLike;
import com.javamentor.developer.social.platform.service.abstracts.model.like.MessageLikeService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageLikeServiceImpl extends GenericServiceAbstract<MessageLike, Long> implements MessageLikeService {

    @Autowired
    public MessageLikeServiceImpl(MessageLikeDao dao) {
        super(dao);
    }
}
