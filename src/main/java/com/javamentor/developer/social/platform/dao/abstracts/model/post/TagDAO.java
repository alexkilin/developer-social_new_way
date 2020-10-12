package com.javamentor.developer.social.platform.dao.abstracts.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.post.Tag;

import java.util.Optional;

public interface TagDAO extends GenericDao<Tag, Long> {
    Optional<Tag> getByName(String name);
}
