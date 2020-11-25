package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;

import java.util.List;

public interface PostDtoDao {

    List<PostDto> getAllPosts(Long userPrincipalId, int currentPage, int itemsOnPage);

    List<PostDto> getPostById(Long postId, Long userPrincipalId);

    List<PostDto> getPostsByTag(String text, Long userPrincipalId, int currentPage, int itemsOnPage);

    List<CommentDto> getCommentsByPostId(Long postId, int currentPage, int itemsOnPage);

    List<MediaPostDto> getMediasByPostId(List<Long> postId);

    List<TagDto> getTagsByPostId(List<Long> postId);

    List<PostDto> getPostsByUserId(Long userId, Long userPrincipalId, int currentPage, int itemsOnPage);

    List <PostDto> getAllBookmarkedPosts(Long userPrincipalId, int currentPage, int itemsOnPage);

    List <TagDto> getAllTags (int currentPage, int itemsOnPage);

}
