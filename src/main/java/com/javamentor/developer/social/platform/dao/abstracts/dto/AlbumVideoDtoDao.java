package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;

import java.util.List;
import java.util.Optional;

public interface AlbumVideoDtoDao {

    List<AlbumVideoDto> getAllByUserId(Long userId);
}
