package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumAudioDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class AlbumAudioDtoServiceImplImpl extends PaginationServiceImpl implements AlbumAudioDtoService {

    public AlbumAudioDtoServiceImplImpl() {
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public PageDto<AlbumAudioDto, ?> getAllByUserId(Map<String, Object> parameters) {
        return super.getPageDto("getAllAlbumAudio", parameters);
    }

}
