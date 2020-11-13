package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;

import java.util.List;

public interface PostDtoService {

    List<PostDto> getAllPosts(Long userPrincipalId);

    List<PostDto> getPostsByTag(String text, Long userPrincipalId);

    List<CommentDto> getCommentsByPostId(Long id);

    List<TagDto> getAllTags();
    
    List<PostDto> getPostsByUserId(Long id, Long userPrincipalId);

    List <PostDto> getAllBookmarkedPosts(Long userPrincipalId);
}
