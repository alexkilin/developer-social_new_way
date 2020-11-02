package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.AlbumDto;

import java.util.List;
import java.util.Optional;

public interface AlbumDtoDao {

    List<AlbumDto> getAllByUserId(Long id);
    Optional<AlbumDto> getById(Long id);

}
