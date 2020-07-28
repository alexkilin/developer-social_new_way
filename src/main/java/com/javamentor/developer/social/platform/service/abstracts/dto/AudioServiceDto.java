package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AudioDto;

import java.util.List;

public interface AudioServiceDto {

    List<AudioDto> getAllAudios();
    List<AudioDto> getPartAudio(int start, int end);
    List<AudioDto> getAudioOfAuthor(String author);
}
