package com.javamentor.developer.social.platform.service.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.UserDao;
import com.javamentor.developer.social.platform.service.abstracts.model.UserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, GenericServiceAbstract<User, Long> {

    private final UserDao userDAO;

    @Autowired
    public UserServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

}
