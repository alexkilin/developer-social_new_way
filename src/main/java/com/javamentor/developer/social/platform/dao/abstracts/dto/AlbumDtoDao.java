package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;

import java.util.List;
import java.util.Optional;

public interface AlbumDtoDao {

    List<AlbumDto> getAllByUserId(Long id);
    List<AlbumDto> getAllByTypeAndUserId(MediaType type, Long userId);
    Optional<AlbumDto> getById(Long id);

}
