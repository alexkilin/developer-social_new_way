package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;

import java.util.List;

public interface AlbumVideoDtoService {

    List<AlbumVideoDto> getAllByUserId(Long userId);
}
