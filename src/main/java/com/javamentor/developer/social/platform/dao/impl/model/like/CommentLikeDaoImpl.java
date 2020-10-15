package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.CommentLikeDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.like.CommentLike;
import org.springframework.stereotype.Repository;

@Repository
public class CommentLikeDaoImpl extends GenericDaoAbstract<CommentLike, Long> implements CommentLikeDao {
}
