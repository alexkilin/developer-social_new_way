package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.FriendDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import org.springframework.stereotype.Repository;

@Repository
public class FriendDaoImpl extends GenericDaoAbstract<Friend, Long> implements FriendDao {
}
