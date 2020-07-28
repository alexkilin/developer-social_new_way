package com.javamentor.developer.social.platform.dao.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.CommentDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.comment.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAOImpl extends GenericDaoAbstract<Comment, Long> implements CommentDAO {
}
