package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.PostLikeDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import org.springframework.stereotype.Repository;

@Repository
public class PostLikeDaoImpl extends GenericDaoAbstract<PostLike, Long> implements PostLikeDao {
}
