package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;

import java.util.List;

public interface PostDtoService {
    List<PostDto> getPosts();

    List<PostDto> getPostsByTag(String text);

    List<CommentDto> getCommentsByPostId(Long id);
}
