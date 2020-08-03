package com.javamentor.developer.social.platform.service.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.UserFriendsDao;
import com.javamentor.developer.social.platform.service.abstracts.model.UserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFriendsServiceImpl implements UserFriendsService {

    private final UserFriendsDao userFriendsDAO;

    @Autowired
    public UserFriendsServiceImpl(UserFriendsDao userFriendsDAO) {
        this.userFriendsDAO = userFriendsDAO;
    }

    @Override
    public List<Friend> getUserFriendsById(Long id) {
        return userFriendsDAO.getUserFriendsById(id);
    }
}
