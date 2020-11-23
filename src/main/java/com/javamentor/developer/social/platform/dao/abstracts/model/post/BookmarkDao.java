package com.javamentor.developer.social.platform.dao.abstracts.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;

import java.util.Optional;

public interface BookmarkDao extends GenericDao<Bookmark, Long> {

    void deleteBookmarkByPostIdAndUserId(Long postId, Long userId);

    Optional<Bookmark> getBookmarkByPostIdAndUserId(Long postId, Long userId);
}
