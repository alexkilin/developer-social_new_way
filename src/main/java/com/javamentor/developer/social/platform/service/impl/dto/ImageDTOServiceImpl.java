package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDTODAO;
import com.javamentor.developer.social.platform.models.dto.ImageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageDTOServiceImpl implements ImageDTOService {

    private final ImageDTODAO DAO;

    @Autowired
    public ImageDTOServiceImpl(ImageDTODAO dao) {
        DAO = dao;
    }


    @Override
    public List<ImageDto> getAllByUserId(Long id) {
        return DAO.getAllByUserId(id);
    }

}
