package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumAudioDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumVideoDtoDao;
import com.javamentor.developer.social.platform.models.dto.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumVideoDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumVideoDtoServiceImpl implements AlbumVideoDtoService {

    private final AlbumVideoDtoDao albumVideoDtoDao;

    @Autowired
    public AlbumVideoDtoServiceImpl(AlbumVideoDtoDao albumVideoDtoDao) {
        this.albumVideoDtoDao = albumVideoDtoDao;
    }

    @Override
    public List<AlbumVideoDto> getAllByUserId(Long userId) {
        return albumVideoDtoDao.getAllByUserId(userId);
    }

    @Override
    public Optional<AlbumDto> getById(Long id) {
        return albumVideoDtoDao.getById(id);
    }
}
