package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.dto.PlaylistGetDto;

import java.util.List;

public interface PlaylistDtoDao {
    PlaylistGetDto getById(Long id);
    List<PlaylistGetDto> getByUserId(Long userId);
    List<AudioDto> getAudioDtoByPlaylistId(Long playlistId);
}
