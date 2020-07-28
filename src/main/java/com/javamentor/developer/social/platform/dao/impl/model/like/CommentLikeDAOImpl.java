package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.CommentDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.like.CommentLikeDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.comment.Comment;
import com.javamentor.developer.social.platform.models.entity.like.CommentLike;
import org.springframework.stereotype.Repository;

@Repository
public class CommentLikeDAOImpl extends GenericDaoAbstract<CommentLike, Long> implements CommentLikeDAO {
}
