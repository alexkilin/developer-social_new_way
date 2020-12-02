package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;

import java.util.Optional;

public interface VideoDtoDao {

    Optional<VideoDto> getVideoOfName(String name);

    boolean addVideoInCollectionsOfUser(Long userId, Long videoId);
}
