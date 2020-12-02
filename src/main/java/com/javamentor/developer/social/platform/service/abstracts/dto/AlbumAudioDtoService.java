package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;

public interface AlbumAudioDtoService {

    PageDto<AlbumAudioDto, ?> getAllByUserId(Map<String, Object> parameters);
}
