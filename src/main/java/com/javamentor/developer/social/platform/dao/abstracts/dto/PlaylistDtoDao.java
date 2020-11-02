package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.PlaylistGetDto;

import java.util.List;
import java.util.Optional;

public interface PlaylistDtoDao {
    Optional<PlaylistGetDto> getById(Long id);
    List<PlaylistGetDto> getByUserId(Long userId);
    List<AudioDto> getAudioDtoByPlaylistId(Long playlistId);
}
