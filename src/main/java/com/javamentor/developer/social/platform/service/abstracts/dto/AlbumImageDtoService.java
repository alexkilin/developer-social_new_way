package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;

import java.util.List;
import java.util.Optional;

public interface AlbumImageDtoService {

    List<AlbumImageDto> getAllByUserId(Long userId);
    Optional<AlbumDto> getById(Long id);
}
