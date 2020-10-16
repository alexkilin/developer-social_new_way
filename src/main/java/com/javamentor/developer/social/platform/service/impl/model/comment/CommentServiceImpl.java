package com.javamentor.developer.social.platform.service.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.CommentDao;
import com.javamentor.developer.social.platform.models.entity.comment.Comment;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.CommentService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends GenericServiceAbstract<Comment, Long> implements CommentService {

    @Autowired
    public CommentServiceImpl(CommentDao dao) {
        super(dao);
    }
}
