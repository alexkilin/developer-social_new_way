package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;
import java.util.Optional;

public interface ImageDtoService {

    PageDto<ImageDto, ?> getAllByUserId(Map<String, Object> parameters);
    PageDto<ImageDto, ?> getAllByAlbumId(Map<String, Object> parameters);
    Optional<ImageDto> getById(Long id);


}
