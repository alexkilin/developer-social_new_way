package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.PostDto;

import java.util.List;

public interface PostDtoService {
    List<PostDto> getPostsByTag(String text);
    List<PostDto> getPostsByUserId(Long id);
}
