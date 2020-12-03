package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;

import java.util.List;

public interface PostDtoDao {

    List<PostDto> getPostById(Long postId, Long userPrincipalId);

    List<MediaPostDto> getMediasByPostId(List<Long> postId);

    List<TagDto> getTagsByPostId(List<Long> postId);
}
