package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;

import java.util.List;

public interface AudioDtoDao {

    List<AudioDto> getAudioOfAuthor(String author, int currentPage, int itemsOnPage);

    List<AudioDto> getAudioOfName(String name, int currentPage, int itemsOnPage);


    Audios getAudioOfId(Long id);

    List<AudioDto> getAudioOfAlbum(String album, int currentPage, int itemsOnPage);

    List<AudioDto> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage);

    List<AudioDto> getAuthorAudioOfUser(Long userId, String author, int currentPage, int itemsOnPage);

    List<AudioDto> getAlbumAudioOfUser(Long userId, String album, int currentPage, int itemsOnPage);

    List<AudioDto> getAudioFromAlbumOfUser(Long albumId, int currentPage, int itemsOnPage);

    List<AudioDto> getAudioFromPlaylist(Long playlistId, int currentPage, int itemsOnPage);

    List<AudioDto> getPartAudio(int currentPage, int itemsOnPage);
}
