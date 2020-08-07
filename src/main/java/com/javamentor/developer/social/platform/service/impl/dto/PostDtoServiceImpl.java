package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostDtoServiceImpl implements PostDtoService {

    final
    PostDtoDao postDtoDao;

    @Autowired
    public PostDtoServiceImpl(PostDtoDao postDtoDao) {
        this.postDtoDao = postDtoDao;
    }

    @Override
    public List<PostDto> getPosts() {
        return postDtoDao.getPosts();
    }

    @Override
    public List<PostDto> getPostsByTag(String text) {
        return postDtoDao.getPostsByTag(text);
    }
}
