package com.javamentor.developer.social.platform.service.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.LikeDao;
import com.javamentor.developer.social.platform.models.entity.like.Like;
import com.javamentor.developer.social.platform.service.abstracts.model.like.LikeService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeServiceImpl extends GenericServiceAbstract<Like, Long> implements LikeService {

    private final LikeDao likeDao;

    @Autowired
    public LikeServiceImpl(LikeDao dao) {
        super(dao);
        this.likeDao = dao;
    }

    @Override
    @Transactional
    public Like createLike(Like like) {
        return likeDao.createLike(like);
    }
}
