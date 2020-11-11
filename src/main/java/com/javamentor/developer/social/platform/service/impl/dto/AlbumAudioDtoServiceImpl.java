package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumAudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumAudioDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumAudioDtoServiceImpl implements AlbumAudioDtoService {

    private final AlbumAudioDtoDao albumAudioDtoDao;

    @Autowired
    public AlbumAudioDtoServiceImpl(AlbumAudioDtoDao albumAudioDtoDao) {
        this.albumAudioDtoDao = albumAudioDtoDao;
    }

    @Override
    public List<AlbumAudioDto> getAllByUserId(Long userId) {
        return albumAudioDtoDao.getAllByUserId(userId);
    }

    @Override
    public Optional<AlbumDto> getById(Long id) {
        return albumAudioDtoDao.getById(id);
    }
}
