package com.javamentor.developer.social.platform.dao.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.UserDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends GenericDaoAbstract<User, Long> implements UserDAO {
}
