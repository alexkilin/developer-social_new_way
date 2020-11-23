package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.webapp.converters.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ImageDtoServiceImpl implements ImageDtoService {
    private final ImageDtoDao dao;

    @Autowired
    public ImageDtoServiceImpl(ImageDtoDao dao) {
        this.dao = dao;

    }


    @Override
    @Transactional
    public List<ImageDto> getAllByUserId(Long userId, int currentPage, int itemsOnPage) {
        return dao.getAllByUserId(userId, currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public List<ImageDto> getAllByAlbumId(Long albumId, int currentPage, int itemsOnPage) {
        return dao.getAllByAlbumId(albumId, currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public Optional<ImageDto> getById(Long id) {
        return dao.getById(id);
    }


}
