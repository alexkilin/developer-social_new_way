package com.javamentor.developer.social.platform.dao.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.PostCommentDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.comment.Comment;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import org.springframework.stereotype.Repository;

@Repository
public class PostCommentDaoImpl extends GenericDaoAbstract<PostComment, Long> implements PostCommentDao {

    @Override
    public void create(PostComment entity) {
        // Comment entity in PostComment is now in detached state, must be in managed state to
        // successfully persist PostComment cause of @MapsId annotation in PostComment
        Comment comment = entityManager.find(Comment.class, entity.getComment().getId());
        entity.setComment(comment);

        super.create(entity);
    }
}
