package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;
import java.util.Optional;

public interface PlaylistDtoService {
    Optional<PlaylistGetDto> getById(Long id);

    PageDto<PlaylistGetDto, ?> getAllByUserId(Map<String, Object> parameters);
}
