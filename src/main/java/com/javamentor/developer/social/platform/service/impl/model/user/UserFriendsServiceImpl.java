package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserFriendsDao;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserFriendsService;
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
