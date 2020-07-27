package com.javamentor.developer.social.platform.dao.impl.model;


import com.javamentor.developer.social.platform.dao.abstracts.model.UserDAO;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends ReadWriteDAOImpl<User, Long> implements UserDAO {
}