package com.javamentor.developer.social.platform.service.impl.model;

import com.javamentor.developer.social.platform.dao.impl.model.UserDAOImpl;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.UserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends GenericServiceAbstract<User, Long, UserDAOImpl> implements UserService {

    @Autowired
    public UserServiceImpl(UserDAOImpl dao) {
        super(dao);
    }
}