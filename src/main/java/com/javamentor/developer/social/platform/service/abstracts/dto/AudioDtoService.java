package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AudioDto;

import java.util.List;
import java.util.Optional;

public interface AudioDtoService {

    List<AudioDto> getAudioOfAuthor(String author);
    AudioDto getAudioOfName(String name);

    List<AudioDto> getAudioOfAlbum(String album);

    List<AudioDto> getAudioOfUser(Long userId);

    List<AudioDto> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage);

    List<AudioDto> getAuthorAudioOfUser(Long userId, String author);

    List<AudioDto> getAlbumAudioOfUser(Long userId, String album);

    boolean addAudioInCollectionsOfUser(Long userId, Long audioId);

    List<AudioDto> getAudioFromAlbumOfUser(Long albumId);
}
