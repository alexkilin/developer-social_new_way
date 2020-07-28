package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostDtoServiceImpl implements PostDtoService {

    public final PostDtoDao postDTODAO;

    @Autowired
    public PostDtoServiceImpl(PostDtoDao postDTODAO) {
        this.postDTODAO = postDTODAO;
    }
}
