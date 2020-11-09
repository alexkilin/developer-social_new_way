package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;

import java.util.List;

public interface AudioDtoDao {

    List<AudioDto> getAudioOfAuthor(String author);

    List<AudioDto> getAudioOfName(String name);


    Audios getAudioOfId(Long id);

    List<AudioDto> getAudioOfAlbum(String album);

    List<AudioDto> getAudioOfUser(Long userId);

    List<AudioDto> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage);

    List<AudioDto> getAuthorAudioOfUser(Long userId, String author);

    List<AudioDto> getAlbumAudioOfUser(Long userId, String album);

    List<AudioDto> getAudioFromAlbumOfUser(Long albumId);

    List<AudioDto> getAudioFromPlaylist(Long playlistId, int offset, int limit);
}
