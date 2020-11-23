package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumImageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumImageDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumImageDtoServiceImpl implements AlbumImageDtoService {

    private final AlbumImageDtoDao albumImageDtoDao;

    @Autowired
    public AlbumImageDtoServiceImpl(AlbumImageDtoDao albumImageDtoDao) {
        this.albumImageDtoDao = albumImageDtoDao;
    }

    @Override
    @Transactional
    public List<AlbumImageDto> getAllByUserId(Long userId, int currentPage, int itemsOnPage) {
        return albumImageDtoDao.getAllByUserId(userId, currentPage, itemsOnPage);
    }

}
