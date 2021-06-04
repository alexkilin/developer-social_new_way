package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioLastPlayedDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;

import java.util.Optional;

public interface AudioDtoDao {

    Audios getAudioOfId(Long id);

    Optional<AudioLastPlayedDto> getLastPlayedAudioDtoByUserId(Long id);
}
