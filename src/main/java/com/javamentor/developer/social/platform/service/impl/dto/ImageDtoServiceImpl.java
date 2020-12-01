package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ImageDtoServiceImpl extends PaginationServiceImpl<ImageDto, Object> implements ImageDtoService {
    private final ImageDtoDao dao;

    @Autowired
    public ImageDtoServiceImpl(ImageDtoDao dao) {
        this.dao = dao;

    }


    @Override
    public PageDto<ImageDto, Object> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllImagesOfUser", parameters);
    }

    @Override
    public PageDto<ImageDto, Object> getAllByAlbumId(Map<String, Object> parameters) {
        return super.getPageDto("getImagesFromAlbumById", parameters);
    }

    @Override
    public Optional<ImageDto> getById(Long id) {
        return dao.getById(id);
    }


}
