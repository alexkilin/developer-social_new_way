package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;

import java.util.List;
import java.util.Optional;

public interface AudioDtoDao {

    List<AudioDto> getAllAudios();
    List<AudioDto> getPartAudio(int currentPage, int itemsOnPage);
    List<AudioDto> getAudioOfAuthor(String author);

    Optional<AudioDto> getAudioOfName(String name);


    Audios getAudioOfId(Long id);

    List<AudioDto> getAudioOfAlbum(String album);

    List<AudioDto> getAudioOfUser(Long userId);

    List<AudioDto> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage);

    List<AudioDto> getAuthorAudioOfUser(Long userId, String author);

    List<AudioDto> getAlbumAudioOfUser(Long userId, String album);

    boolean addAudioInCollectionsOfUser(Long userId, Long audioId);
}
