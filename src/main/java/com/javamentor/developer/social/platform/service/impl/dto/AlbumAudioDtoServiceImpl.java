package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumAudioDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlbumAudioDtoServiceImpl extends PaginationServiceImpl<AlbumAudioDto, Object> implements AlbumAudioDtoService {

    public AlbumAudioDtoServiceImpl() {
    }

    @Override
    public PageDto<AlbumAudioDto, Object> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllAlbumAudio", parameters);
    }

}
