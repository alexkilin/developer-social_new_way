package com.javamentor.developer.social.platform.dao.abstracts.model;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.user.User;

import java.util.List;

public interface UserDao extends GenericDao<User, Long> {

    List<User> getAll();
}