package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;

public interface AudioDtoService {

    PageDto<AudioDto, ?> getAudioOfAuthor(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getAudioOfName(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getAudioOfAlbum(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getPartAudioOfUser(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getAuthorAudioOfUser(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getAlbumAudioOfUser(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getAudioFromAlbumOfUser(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getAudioFromPlaylist(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getPartAudio(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getPartAudiosOfFriends(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getAudioSortedByListening(Map<String, Object> parameters);

    PageDto<AudioDto, ?> getAudioByGenre(Map<String, Object> parameters);
}
