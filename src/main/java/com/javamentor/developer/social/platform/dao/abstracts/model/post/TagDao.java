package com.javamentor.developer.social.platform.dao.abstracts.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.post.Tag;

import java.util.Optional;

public interface TagDao extends GenericDao<Tag, Long> {

    Optional<Tag> getTagByText(String text);
}