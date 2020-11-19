package com.javamentor.developer.social.platform.service.abstracts.model.like;

import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface PostLikeService extends GenericService<PostLike, Long> {

    Optional<PostLike> getPostLikeByPostIdAndUserId(Long postId, Long userId);
}
