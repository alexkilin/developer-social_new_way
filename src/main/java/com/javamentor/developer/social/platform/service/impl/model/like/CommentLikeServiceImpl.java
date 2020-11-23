package com.javamentor.developer.social.platform.service.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.CommentLikeDao;
import com.javamentor.developer.social.platform.models.entity.like.CommentLike;
import com.javamentor.developer.social.platform.service.abstracts.model.like.CommentLikeService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl extends GenericServiceAbstract<CommentLike, Long> implements CommentLikeService {

    public CommentLikeServiceImpl(CommentLikeDao dao) {
        super(dao);
    }
}
