package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioLastPlayedDto;

import java.util.Optional;

public interface AudioLastPlayedDtoService  {

    Optional<AudioLastPlayedDto> getAudioLastPlayedOfUserById(Long id);
}
