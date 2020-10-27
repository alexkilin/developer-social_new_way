package com.javamentor.developer.social.platform.dao.abstracts.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.post.Post;

public interface PostDao extends GenericDao<Post, Long> {
    void deletePostFromUserWallById(Long id);
}
