package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDTODAO;
import com.javamentor.developer.social.platform.service.abstracts.dto.PostDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostDTOServiceImpl implements PostDTOService {

    public final PostDTODAO postDTODAO;

    @Autowired
    public PostDTOServiceImpl(PostDTODAO postDTODAO) {
        this.postDTODAO = postDTODAO;
    }
}
