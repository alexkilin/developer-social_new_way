package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumVideoDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumVideoDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlbumVideoDtoServiceImpl implements AlbumVideoDtoService {

    private final AlbumVideoDtoDao albumVideoDtoDao;

    @Autowired
    public AlbumVideoDtoServiceImpl(AlbumVideoDtoDao albumVideoDtoDao) {
        this.albumVideoDtoDao = albumVideoDtoDao;
    }

    @Override
    @Transactional
    public List<AlbumVideoDto> getAllByUserId(Long userId) {
        return albumVideoDtoDao.getAllByUserId(userId);
    }

}
