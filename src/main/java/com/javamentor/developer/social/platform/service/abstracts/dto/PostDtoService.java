package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.List;
import java.util.Map;

public interface PostDtoService {

    PageDto<PostDto, Object> getAllPosts(Map<String, Object> parameters);

    List<PostDto> getPostById(Long postId, Long userPrincipalId);

    PageDto<PostDto, Object> getPostsByTag(Map<String, Object> parameters);

    PageDto<CommentDto, Object> getCommentsByPostId(Map<String, Object> parameters);

    PageDto<TagDto, Object> getAllTags(Map<String, Object> parameters);

    PageDto<PostDto, Object> getPostsByUserId(Map<String, Object> parameters);

    PageDto <PostDto, Object> getAllBookmarkedPosts(Map<String, Object> parameters);
}
