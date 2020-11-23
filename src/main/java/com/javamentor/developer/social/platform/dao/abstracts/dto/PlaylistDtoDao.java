package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;

import java.util.List;
import java.util.Optional;

public interface PlaylistDtoDao {
    Optional<PlaylistGetDto> getById(Long id);
    List<PlaylistGetDto> getByUserId(Long userId, int currentPage, int itemsOnPage);
    List<AudioDto> getAudioDtoByPlaylistId(Long playlistId);
}
