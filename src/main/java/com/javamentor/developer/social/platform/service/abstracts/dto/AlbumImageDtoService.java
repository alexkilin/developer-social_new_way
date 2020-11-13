package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;

import java.util.List;

public interface AlbumImageDtoService {

    List<AlbumImageDto> getAllByUserId(Long userId);
}
