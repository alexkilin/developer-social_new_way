package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDTODAO;
import com.javamentor.developer.social.platform.models.dto.ImageCreateDto;
import com.javamentor.developer.social.platform.models.dto.ImageDto;
import com.javamentor.developer.social.platform.models.dto.PageDto;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDTOService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.service.impl.PaginationDtoService;
import com.javamentor.developer.social.platform.webapp.converters.ImageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImageDTOServiceImpl extends PaginationDtoService<ImageDto, Object> implements ImageDTOService {

    private final ImageDTODAO dao;
    private final ImageService imageService;
    private final ImageConverter imageConverter;

    @Autowired
    public ImageDTOServiceImpl(ImageDTODAO dao, ImageService imageService, ImageConverter imageConverter) {
        super(dao);
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
    public ImageDto create(ImageCreateDto imageCreateDto) {
        Image newImage = imageConverter.toEntity(imageCreateDto);
        imageService.create(newImage);
        return imageConverter.toImageDto(newImage);
    }

    @Override
    public Optional<ImageDto> getById(Long id) {
        return dao.getById(id);
    }


}
