package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.FollowerDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Follower;
import org.springframework.stereotype.Repository;

@Repository
public class FollowerDaoImpl extends GenericDaoAbstract<Follower, Long> implements FollowerDao {
}
