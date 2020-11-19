package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;

import java.util.List;

public interface VideoDtoService {

    VideoDto getVideoOfName(String name);

    List<VideoDto> getVideoOfUser(Long userId);

    List<VideoDto> getPartVideoOfUser(Long userId, int currentPage, int itemsOnPage);

    List<VideoDto> getAlbumVideoOfUser(Long userId, String album);

    List<VideoDto> getVideoFromAlbumOfUser(Long albumId);

    List<VideoDto> getPartVideo(int currentPage, int itemsOnPage);
}
