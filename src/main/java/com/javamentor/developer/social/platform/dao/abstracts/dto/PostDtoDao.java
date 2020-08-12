package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.PostDto;

import java.util.List;

public interface PostDtoDao {
    List<PostDto> getPostsByTag(String text);
    List<PostDto> getPostsByUserId(Long id);
}
