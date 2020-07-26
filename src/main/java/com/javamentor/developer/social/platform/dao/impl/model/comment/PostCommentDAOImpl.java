package com.javamentor.developer.social.platform.dao.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.PostCommentDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import org.springframework.stereotype.Repository;

@Repository
public class PostCommentDAOImpl extends GenericDaoAbstract<PostComment, Long> implements PostCommentDAO {
}
