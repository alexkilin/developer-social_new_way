package com.javamentor.developer.social.platform.dao.abstracts.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.dto.tags.MostPopularTagsInPostsDto;
import com.javamentor.developer.social.platform.models.entity.post.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends GenericDao<Tag, Long> {

    Optional<Tag> getTagByText(String text);

    List<Tag> getTagsByText(List<String> texts);

    List<MostPopularTagsInPostsDto> getMostPopularTagsInPosts();
}
