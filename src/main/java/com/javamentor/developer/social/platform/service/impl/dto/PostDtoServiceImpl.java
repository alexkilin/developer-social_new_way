package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.service.impl.dto.page.PostPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PostDtoServiceImpl extends PostPaginationService implements PostDtoService {

    private final PostDtoDao postDtoDao;
    private final UserService userService;

    @Autowired
    public PostDtoServiceImpl(PostDtoDao postDtoDao, UserService userService) {
        this.postDtoDao = postDtoDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    public PageDto<PostDto, ?> getPostsByTag(Map<String, Object> parameters) {
        return super.getPostPageDto("getPostsByTag", parameters);
    }

    @Override
    @Transactional
    public List<PostDto> getPostById(Long postId, Long userPrincipalId) {
        return postDtoDao.getPostById(postId, userPrincipalId);
    }

    @Override
    @Transactional
    public PageDto<PostDto, ?> getPostsByUserId(Map<String, Object> parameters) {
        return super.getPostPageDto("getPostsByUserId", parameters);
    }

    @Override
    @Transactional
    public PageDto<PostDto, ?> getAllBookmarkedPosts(Map<String, Object> parameters) {
        return super.getPostPageDto("getAllBookmarkedPosts", parameters);
    }

    @Override
    @Transactional
    public PageDto<CommentDto, ?> getCommentsByPostId(Map<String, Object> parameters) {
        return super.getPageDto("showPostComments", parameters);
    }

    @Override
    @Transactional
    public PageDto<TagDto, ?> getAllTags(Map<String, Object> parameters) {
        return super.getPageDto("getAllTags", parameters);
    }

    @Override
    @Transactional
    public PageDto<PostDto, ?> getAllPosts(Map<String, Object> parameters) {
        return super.getPostPageDto("getAllPosts", parameters);
    }
}
