package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class ImageDtoServiceImplImpl extends PaginationServiceImpl implements ImageDtoService {
    private final ImageDtoDao dao;

    @Autowired
    public ImageDtoServiceImplImpl(ImageDtoDao dao) {
        this.dao = dao;

    }


    @Override
    @Transactional
    public PageDto<ImageDto, ?> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllImagesOfUser", parameters);
    }

    @Override
    @Transactional
    public PageDto<ImageDto, ?> getAllByAlbumId(Map<String, Object> parameters) {
        return super.getPageDto("getImagesFromAlbumById", parameters);
    }

    @Override
    @Transactional
    public Optional<ImageDto> getById(Long id) {
        return dao.getById(id);
    }


}
