package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.List;
import java.util.Map;

public interface VideoDtoService {

    PageDto<VideoDto, ?> getVideoOfAuthor(Map<String, Object> parameters);

    VideoDto getVideoOfName(String name);

    PageDto<VideoDto, ?> getPartVideoOfUser(Map<String, Object> parameters);

    PageDto<VideoDto, ?> getAuthorVideoOfUser(Map<String, Object> parameters);

    PageDto<VideoDto, ?> getAlbumVideoOfUser(Map<String, Object> parameters);

    PageDto<VideoDto, ?> getVideoFromAlbumOfUser(Map<String, Object> parameters);

    PageDto<VideoDto, ?> getPartVideo(Map<String, Object> parameters);
}
