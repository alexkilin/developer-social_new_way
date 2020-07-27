package com.javamentor.developer.social.platform.service.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.UserFriendsDAO;
import com.javamentor.developer.social.platform.service.abstracts.model.UserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFriendsServiceImpl extends ReadWriteServiceImpl<Friend, Long> implements UserFriendsService {

    private final UserFriendsDAO userFriendsDAO;

    @Autowired
    public UserFriendsServiceImpl(UserFriendsDAO userFriendsDAO) {
        super(userFriendsDAO);
        this.userFriendsDAO = userFriendsDAO;
    }
}
