package com.javamentor.developer.social.platform.service.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.PostLikeDao;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import com.javamentor.developer.social.platform.service.abstracts.model.like.PostLikeService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostLikeServiceImpl extends GenericServiceAbstract<PostLike, Long> implements PostLikeService {

    private final PostLikeDao postLikeDao;

    @Autowired
    public PostLikeServiceImpl(PostLikeDao dao) {
        super(dao);
        this.postLikeDao = dao;
    }

    @Override
    @Transactional
    public Optional<PostLike> getPostLikeByPostIdAndUserId(Long postId, Long userId) {
        return postLikeDao.getPostLikeByPostIdAndUserId(postId, userId);
    }

    @Override
    @Transactional
    public void create(PostLike entity) {
        postLikeDao.create(entity);
    }

    @Override
    @Transactional
    public void delete(PostLike entity) {
        postLikeDao.delete(entity);
    }
}
