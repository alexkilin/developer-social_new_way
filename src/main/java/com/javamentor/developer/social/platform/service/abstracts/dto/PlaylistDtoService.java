package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;

import java.util.List;
import java.util.Optional;

public interface PlaylistDtoService {
    Optional<PlaylistGetDto> getById(Long id);

    List<PlaylistGetDto> getAllByUserId(Long userId, int currentPage, int itemsOnPage);
}
