package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.models.dto.ImageCreateDto;
import com.javamentor.developer.social.platform.models.dto.ImageDto;
import com.javamentor.developer.social.platform.models.entity.media.Image;
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
    public List<ImageDto> getAllByUserId(int offset, int limit, Long id) {
        return dao.getAllByUserId(offset, limit, id);
    }

    @Override
    public List<ImageDto> getAllByAlbumId(int offset, int limit, Long id) {
        return dao.getAllByAlbumId(offset, limit, id);
    }

    @Override
    public Optional<ImageDto> getById(Long id) {
        return dao.getById(id);
    }


}
