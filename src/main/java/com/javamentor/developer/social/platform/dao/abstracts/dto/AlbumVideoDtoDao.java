package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;

import java.util.List;

public interface AlbumVideoDtoDao {

    List<AlbumVideoDto> getAllByUserId(Long userId);
}
