package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PostPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostDtoServiceImpl extends PostPaginationService<Object, Object> implements PostDtoService {

    private final PostDtoDao postDtoDao;

    @Autowired
    public PostDtoServiceImpl(PostDtoDao postDtoDao) {
        this.postDtoDao = postDtoDao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<PostDto, Object> getPostsByTag(Map<String, Object> parameters) {
        return (PageDto<PostDto, Object>) super.getPostPageDto("getPostsByTag", parameters);
    }

    @Override
    public List<PostDto> getPostById(Long postId, Long userPrincipalId) {
        return postDtoDao.getPostById(postId, userPrincipalId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<PostDto, Object> getPostsByUserId(Map<String, Object> parameters) {
        return (PageDto<PostDto, Object>) super.getPostPageDto("getPostsByUserId", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<PostDto, Object> getAllBookmarkedPosts(Map<String, Object> parameters) {
        return (PageDto<PostDto, Object>) super.getPostPageDto("getAllBookmarkedPosts", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<PostDto, Object> getPostsByAllFriendsAndGroups(Map<String, Object> parameters) {
        return (PageDto<PostDto, Object>) super.getPostPageDto("getPostsByAllFriendsAndGroups", parameters);
    }

    @Override
    public PageDto<PostDto, Object> getAllBookmarks(Map<String, Object> parameters) {
        return (PageDto<PostDto, Object>) super.getPostPageDto("getAllBookmarks", parameters);
    }

    @Override
    public PageDto<Object, Object> getCommentsByPostId(Map<String, Object> parameters) {
        return super.getPageDto("showPostComments", parameters);
    }

    @Override
    public PageDto<Object, Object> getAllTags(Map<String, Object> parameters) {
        return super.getPageDto("getAllTags", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<PostDto, Object> getAllPosts(Map<String, Object> parameters) {
        return (PageDto<PostDto, Object>) super.getPostPageDto("getAllPosts", parameters);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<PostDto, Object> getAllPostsByTopic(Map<String, Object> parameters) {
        return (PageDto<PostDto, Object>) super.getPostPageDto("getAllPostsByTopic", parameters);
    }
}
