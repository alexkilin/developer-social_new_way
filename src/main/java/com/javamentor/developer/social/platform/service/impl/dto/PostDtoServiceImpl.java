package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostDtoServiceImpl implements PostDtoService {

    private final PostDtoDao postDtoDao;

    @Autowired
    public PostDtoServiceImpl(PostDtoDao postDtoDao) {
        this.postDtoDao = postDtoDao;
    }

    @Override
    public List<PostDto> getPostsByTag(String text, Long userPrincipalId) {
        return postDtoDao.getPostsByTag(text, userPrincipalId);
    }

    @Override
    public List<PostDto> getPostsByUserId(Long id, Long userPrincipalId) {
        return postDtoDao.getPostsByUserId(id, userPrincipalId);
    }

    @Override
    public List<PostDto> getAllBookmarkedPosts(Long userPrincipalId) {
        return postDtoDao.getAllBookmarkedPosts(userPrincipalId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long id) {
        return postDtoDao.getCommentsByPostId(id);
    }

    @Override
    public List<PostDto> getAllPosts(Long userPrincipalId) {
        List<PostDto> postDtoList = postDtoDao.getAllPosts(userPrincipalId);
        return postDtoList;
    }
}
