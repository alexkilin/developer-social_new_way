package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;

import java.util.Optional;

public interface ImageDtoDao {

    Optional<ImageDto> getById(Long id);
}
