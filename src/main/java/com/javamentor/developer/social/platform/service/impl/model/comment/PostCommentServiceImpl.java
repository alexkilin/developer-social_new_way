package com.javamentor.developer.social.platform.service.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.comment.PostCommentDAO;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.PostCommentService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostCommentServiceImpl extends GenericServiceAbstract<PostComment, Long> implements PostCommentService {

    @Autowired
    public PostCommentServiceImpl(PostCommentDAO dao) {
        super(dao);
    }
}
