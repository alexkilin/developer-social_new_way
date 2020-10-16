package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.LikeDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.like.Like;
import org.springframework.stereotype.Repository;

@Repository
public class LikeDaoImpl extends GenericDaoAbstract<Like, Long> implements LikeDao {
}
