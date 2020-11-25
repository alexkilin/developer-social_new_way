package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.List;
import java.util.Map;

public interface AlbumVideoDtoService {

    PageDto<AlbumVideoDto, ?> getAllByUserId(Map<String, Object> parameters);
}
