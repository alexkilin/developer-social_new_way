package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostDTOServiceImpl implements PostDTOService {

    public final PostDtoDao postDTODAO;

    @Autowired
    public PostDTOServiceImpl(PostDtoDao postDTODAO) {
        this.postDTODAO = postDTODAO;
    }
}
