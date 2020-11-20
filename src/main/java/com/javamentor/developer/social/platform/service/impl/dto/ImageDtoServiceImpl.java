package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDtoService;
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
    public List<ImageDto> getAllByUserId(int offset, int limit, Long id) {
        return dao.getAllByUserId(offset, limit, id);
    }

    @Override
    @Transactional
    public List<ImageDto> getAllByAlbumId(int offset, int limit, Long id) {
        return dao.getAllByAlbumId(offset, limit, id);
    }

    @Override
    @Transactional
    public Optional<ImageDto> getById(Long id) {
        return dao.getById(id);
    }

}
