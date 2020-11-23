package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;

import java.util.List;

public interface PostDtoService {

    List<PostDto> getAllPosts(Long userPrincipalId, int currentPage, int itemsOnPage);

    List<PostDto> getPostById(Long postId, Long userPrincipalId);

    List<PostDto> getPostsByTag(String tagText, Long userPrincipalId, int currentPage, int itemsOnPage);

    List<CommentDto> getCommentsByPostId(Long id, int currentPage, int itemsOnPage);

    List<TagDto> getAllTags(int currentPage, int itemsOnPage);
    
    List<PostDto> getPostsByUserId(Long id, Long userPrincipalId, int currentPage, int itemsOnPage);

    List <PostDto> getAllBookmarkedPosts(Long userPrincipalId, int currentPage, int itemsOnPage);
}
