package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<PostDto> getPostsByTag(String tagText, Long userPrincipalId, int currentPage, int itemsOnPage) {
        return postDtoDao.getPostsByTag(tagText, userPrincipalId, currentPage, itemsOnPage);
    }

    @Override
    public List<PostDto> getPostById(Long postId, Long userPrincipalId) {
        return postDtoDao.getPostById(postId, userPrincipalId);
    }

    @Override
    public List<PostDto> getPostsByUserId(Long id, Long userPrincipalId, int currentPage, int itemsOnPage) {
        return postDtoDao.getPostsByUserId(id, userPrincipalId, currentPage, itemsOnPage);
    }

    @Override
    public List<PostDto> getAllBookmarkedPosts(Long userPrincipalId, int currentPage, int itemsOnPage) {
        return postDtoDao.getAllBookmarkedPosts(userPrincipalId, currentPage, itemsOnPage);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long id, int currentPage, int itemsOnPage) {
        return postDtoDao.getCommentsByPostId(id, currentPage, itemsOnPage);
    }

    @Override
    public List<TagDto> getAllTags(int currentPage, int itemsOnPage) {
        return postDtoDao.getAllTags(currentPage, itemsOnPage);
    }

    @Override
    public List<PostDto> getAllPosts(Long userPrincipalId, int currentPage, int itemsOnPage) {
        List<PostDto> postDtoList = postDtoDao.getAllPosts(userPrincipalId, currentPage, itemsOnPage);
        return postDtoList;
    }
}
