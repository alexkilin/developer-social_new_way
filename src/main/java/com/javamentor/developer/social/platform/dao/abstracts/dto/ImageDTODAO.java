package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.dao.abstracts.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.ImageDto;

import java.util.List;
import java.util.Optional;

public interface ImageDTODAO extends PaginationDao<ImageDto, Object> {

    List<ImageDto> getAllByUserId(int offset, int limit, Long id);
    List<ImageDto> getAllByAlbumId(int offset, int limit, Long id);
    Optional<ImageDto> getById(Long id);

}
