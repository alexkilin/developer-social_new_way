package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostDtoServiceImpl implements PostDtoService {

    private final PostDtoDao postDtoDao;
    private final UserService userService;

    @Autowired
    public PostDtoServiceImpl(PostDtoDao postDtoDao, UserService userService) {
        this.postDtoDao = postDtoDao;
        this.userService = userService;
    }

    @Override
    @Transactional
    public List<PostDto> getPostsByTag(String text) {
        Long userPrincipalId = userService.getPrincipal().getUserId();
        return postDtoDao.getPostsByTag(text, userPrincipalId);
    }

    @Override
    @Transactional
    public List<PostDto> getPostById(Long postId, Long userPrincipalId) {
        return postDtoDao.getPostById(postId, userPrincipalId);
    }

    @Override
    @Transactional
    public List<PostDto> getPostsByUserId(Long id) {
        Long userPrincipalId = userService.getPrincipal().getUserId();
        return postDtoDao.getPostsByUserId(id, userPrincipalId);
    }

    @Override
    @Transactional
    public List<PostDto> getAllBookmarkedPosts() {
        Long userPrincipalId = userService.getPrincipal().getUserId();
        return postDtoDao.getAllBookmarkedPosts(userPrincipalId);
    }

    @Override
    @Transactional
    public List<CommentDto> getCommentsByPostId(Long id) {
        return postDtoDao.getCommentsByPostId(id);
    }

    @Override
    @Transactional
    public List<TagDto> getAllTags() {
        return postDtoDao.getAllTags();
    }

    @Override
    @Transactional
    public List<PostDto> getAllPosts() {
        Long userPrincipalId = userService.getPrincipal().getUserId();
        return postDtoDao.getAllPosts(userPrincipalId);
    }
}
