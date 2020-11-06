package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;

import java.util.List;
import java.util.Optional;

public interface AlbumDtoService {

    List<AlbumDto> getAllByUserId(Long id);
    Optional<AlbumDto> getById(Long id);
}
