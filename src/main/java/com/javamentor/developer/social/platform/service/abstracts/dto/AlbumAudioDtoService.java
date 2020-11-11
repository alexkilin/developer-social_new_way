package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;

import java.util.List;

public interface AlbumAudioDtoService {

    List<AlbumAudioDto> getAllByUserId(Long userId);
}
