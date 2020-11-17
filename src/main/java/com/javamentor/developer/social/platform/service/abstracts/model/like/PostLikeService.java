package com.javamentor.developer.social.platform.service.abstracts.model.like;

import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.List;

public interface PostLikeService extends GenericService<PostLike, Long> {

    List<PostLike> getPostLikeByPostIdAndUserId(Long postId, Long userId);
}
