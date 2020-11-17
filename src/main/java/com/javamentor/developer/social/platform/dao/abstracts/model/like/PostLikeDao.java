package com.javamentor.developer.social.platform.dao.abstracts.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;

import java.util.List;

public interface PostLikeDao extends GenericDao<PostLike, Long> {

    List<PostLike> getPostLikeByPostIdAndUserId(Long postId, Long userId);
}
