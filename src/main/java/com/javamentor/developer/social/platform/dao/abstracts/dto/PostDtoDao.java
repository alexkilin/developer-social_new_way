package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;

import java.util.List;

public interface PostDtoDao {
    List<PostDto> getPosts();
    List<PostDto> getPostsByTag(String text);

    List<CommentDto> getCommentsByPostId(Long id);

    List<MediaPostDto> getMediasByPostId(Long id);

    List<TagDto> getTagsByPostId(Long id);
}
