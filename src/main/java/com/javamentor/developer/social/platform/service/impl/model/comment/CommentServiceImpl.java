package com.javamentor.developer.social.platform.service.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.comment.CommentDAO;
import com.javamentor.developer.social.platform.models.entity.comment.Comment;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.CommentService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends GenericServiceAbstract<Comment, Long> implements CommentService {

    @Autowired
    public CommentServiceImpl(CommentDAO dao) {
        super(dao);
    }
}
