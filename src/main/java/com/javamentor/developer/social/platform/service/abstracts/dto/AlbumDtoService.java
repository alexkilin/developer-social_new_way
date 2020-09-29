package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.AlbumImageDTO;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface AlbumDtoService {

    List<AlbumDto> getAllByUserId(Long id);
    AlbumDto createAlbumImage(AlbumCreateDto albumCreateDto);
    Optional<AlbumDto> getById(Long id);
}
