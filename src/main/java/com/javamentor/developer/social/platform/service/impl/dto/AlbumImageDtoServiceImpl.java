package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumImageDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumVideoDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumImageDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumImageDtoServiceImpl implements AlbumImageDtoService {

    private final AlbumImageDtoDao albumImageDtoDao;

    @Autowired
    public AlbumImageDtoServiceImpl(AlbumImageDtoDao albumImageDtoDao) {
        this.albumImageDtoDao = albumImageDtoDao;
    }

    @Override
    public List<AlbumImageDto> getAllByUserId(Long userId) {
        return albumImageDtoDao.getAllByUserId(userId);
    }

}
