package com.javamentor.developer.social.platform.dao.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.PostCommentDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import org.springframework.stereotype.Repository;

@Repository
public class PostCommentDaoImpl extends GenericDaoAbstract<PostComment, Long> implements PostCommentDao {
}
