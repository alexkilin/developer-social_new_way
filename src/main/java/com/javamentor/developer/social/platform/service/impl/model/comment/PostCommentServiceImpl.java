package com.javamentor.developer.social.platform.service.impl.model.comment;

import com.javamentor.developer.social.platform.dao.abstracts.model.comment.PostCommentDao;
import com.javamentor.developer.social.platform.models.entity.comment.PostComment;
import com.javamentor.developer.social.platform.service.abstracts.model.comment.PostCommentService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class PostCommentServiceImpl extends GenericServiceAbstract<PostComment, Long> implements PostCommentService {

    public PostCommentServiceImpl(PostCommentDao dao) {
        super(dao);
    }
}
