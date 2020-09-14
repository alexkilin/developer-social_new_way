package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.dto.PlaylistGetDto;

import java.util.List;

public interface PlaylistDtoService {
    PlaylistGetDto getById(Long id);
    List<PlaylistGetDto> getAllByUserId(Long userId);
    PlaylistGetDto create(PlaylistCreateDto playlistCreateDto);
}
