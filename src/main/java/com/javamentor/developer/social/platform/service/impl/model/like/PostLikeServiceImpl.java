package com.javamentor.developer.social.platform.service.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.PostLikeDao;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import com.javamentor.developer.social.platform.service.abstracts.model.like.PostLikeService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl extends GenericServiceAbstract<PostLike, Long> implements PostLikeService {

    @Autowired
    public PostLikeServiceImpl(PostLikeDao dao) {
        super(dao);
    }
}
