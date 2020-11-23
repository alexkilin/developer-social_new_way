package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.webapp.converters.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageDtoServiceImpl implements ImageDtoService {

    private final ImageDtoDao dao;
    private final ImageService imageService;
    private final ImageConverter imageConverter;

    @Autowired
    public ImageDtoServiceImpl(ImageDtoDao dao, ImageService imageService, ImageConverter imageConverter) {
        this.dao = dao;
        this.imageService = imageService;
        this.imageConverter = imageConverter;
    }


    @Override
    public List<ImageDto> getAllByUserId(Long userId, int currentPage, int itemsOnPage) {
        return dao.getAllByUserId(userId, currentPage, itemsOnPage);
    }

    @Override
    public List<ImageDto> getAllByAlbumId(Long albumId, int currentPage, int itemsOnPage) {
        return dao.getAllByAlbumId(albumId, currentPage, itemsOnPage);
    }

    @Override
    public Optional<ImageDto> getById(Long id) {
        return dao.getById(id);
    }


}
