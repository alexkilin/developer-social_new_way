package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;

public interface AlbumImageDtoService {

    PageDto<AlbumImageDto, ?> getAllByUserId(Map<String, Object> parameters);
}
