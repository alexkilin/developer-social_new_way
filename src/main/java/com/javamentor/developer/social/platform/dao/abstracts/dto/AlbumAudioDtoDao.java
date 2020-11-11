package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;

import java.util.List;

public interface AlbumAudioDtoDao {

    List<AlbumAudioDto> getAllByUserId(Long userId);
}
