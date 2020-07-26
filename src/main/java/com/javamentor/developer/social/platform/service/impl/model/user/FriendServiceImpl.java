package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.user.FriendDAO;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import com.javamentor.developer.social.platform.service.abstracts.model.user.FriendService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl extends GenericServiceAbstract<Friend, Long> implements FriendService {

    @Autowired
    public FriendServiceImpl(FriendDAO dao) {
        super(dao);
    }
}
