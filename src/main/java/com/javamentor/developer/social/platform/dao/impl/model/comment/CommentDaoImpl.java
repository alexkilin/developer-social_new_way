package com.javamentor.developer.social.platform.dao.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.CommentDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.comment.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentDaoImpl extends GenericDaoAbstract<Comment, Long> implements CommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Comment createComment(Comment comment) {
        return entityManager.merge(comment);
    }
}
