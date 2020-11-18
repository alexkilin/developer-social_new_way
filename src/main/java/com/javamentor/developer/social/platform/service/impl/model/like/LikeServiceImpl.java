package com.javamentor.developer.social.platform.service.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.LikeDao;
import com.javamentor.developer.social.platform.models.entity.like.Like;
import com.javamentor.developer.social.platform.service.abstracts.model.like.LikeService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl extends GenericServiceAbstract<Like, Long> implements LikeService {

    public LikeServiceImpl(LikeDao dao) {
        super(dao);
    }
}
