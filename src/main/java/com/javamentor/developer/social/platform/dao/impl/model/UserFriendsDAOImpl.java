package com.javamentor.developer.social.platform.dao.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.UserFriendsDAO;
import org.springframework.stereotype.Repository;

@Repository
public class UserFriendsDAOImpl extends ReadWriteDAOImpl<Friend, Long> implements UserFriendsDAO {
}
