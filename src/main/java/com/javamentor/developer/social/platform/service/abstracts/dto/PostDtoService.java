package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.List;
import java.util.Map;

public interface PostDtoService {

    PageDto<PostDto, ?> getAllPosts(Map<String, Object> parameters);

    List<PostDto> getPostById(Long postId, Long userPrincipalId);

    PageDto<PostDto, ?> getPostsByTag(Map<String, Object> parameters);

    PageDto<CommentDto, ?> getCommentsByPostId(Map<String, Object> parameters);

    PageDto<TagDto, ?> getAllTags(Map<String, Object> parameters);

    PageDto<PostDto, ?> getPostsByUserId(Map<String, Object> parameters);

    PageDto <PostDto, ?> getAllBookmarkedPosts(Map<String, Object> parameters);
}
