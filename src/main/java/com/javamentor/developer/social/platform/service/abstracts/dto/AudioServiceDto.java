package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AudioDto;

import java.util.List;
import java.util.Optional;

public interface AudioServiceDto {

    Optional<List<AudioDto>> getAllAudios();
    Optional<List<AudioDto>> getPartAudio(int currentPage, int itemsOnPage);
    Optional<List<AudioDto>> getAudioOfAuthor(String author);
    Optional<AudioDto> getAudioOfName(String name);

    Optional<List<AudioDto>> getAudioOfAlbum(String album);

    Optional<List<AudioDto>> getAudioOfUser(Long userId);

    Optional<List<AudioDto>> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage);

    Optional<List<AudioDto>> getAuthorAudioOfUser(Long userId, String author);

    Optional<List<AudioDto>> getAlbumAudioOfUser(Long userId, String album);

    boolean addAudioInCollectionsOfUser(Long userId, Long audioId);
}
