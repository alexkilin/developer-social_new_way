package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.ImageCreateDto;
import com.javamentor.developer.social.platform.models.dto.ImageDto;

import java.util.List;
import java.util.Optional;

public interface ImageDTOService {

    List<ImageDto> getAllByUserId(int offset, int limit, Long id);
    List<ImageDto> getAllByAlbumId(int offset, int limit, Long id);
    ImageDto create(ImageCreateDto imageCreateDto);
    Optional<ImageDto> getById(Long id);


}
