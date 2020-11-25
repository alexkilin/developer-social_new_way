package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumAudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumAudioDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.page.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AlbumAudioDtoServiceImpl extends PaginationService implements AlbumAudioDtoService {

    private final AlbumAudioDtoDao albumAudioDtoDao;

    @Autowired
    public AlbumAudioDtoServiceImpl(AlbumAudioDtoDao albumAudioDtoDao) {
        this.albumAudioDtoDao = albumAudioDtoDao;
    }

    @Override
    @Transactional
    public PageDto<AlbumAudioDto, ?> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllAlbumAudio", parameters);
    }

}
