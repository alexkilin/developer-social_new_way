package com.javamentor.developer.social.platform.service.abstracts.model.post;

import com.javamentor.developer.social.platform.models.entity.post.Tag;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.List;
import java.util.Optional;

public interface TagService extends GenericService<Tag, Long> {

    Optional<Tag> getTagByText(String text);

    Optional<List<Tag>> getTagsByText(List<String> texts);
}
