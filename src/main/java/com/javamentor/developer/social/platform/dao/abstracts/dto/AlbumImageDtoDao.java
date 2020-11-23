package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;

import java.util.List;

public interface AlbumImageDtoDao {

    List<AlbumImageDto> getAllByUserId(Long userId, int currentPage, int itemsOnPage);
}
