package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;

import java.util.List;
import java.util.Optional;

public interface ImageDtoDao {

    List<ImageDto> getAllByUserId(Long userId, int currentPage, int itemsOnPage);
    List<ImageDto> getAllByAlbumId(Long albumId, int currentPage, int itemsOnPage);
    Optional<ImageDto> getById(Long id);

}
