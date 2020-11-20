package com.javamentor.developer.social.platform.service.abstracts.model.post;

import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface BookmarkService extends GenericService<Bookmark, Long> {

    void deleteBookmarkByPostIdAndUserId(Long postId, Long userId);
}
