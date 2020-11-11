package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class VideoConverter {

    @Mapping(source = "videoDto.url", target = "media.url")
    @Mapping(source = "videoDto.icon", target = "icon")
    @Mapping(source = "videoDto.name", target = "name")
    @Mapping(source = "mediaType", target = "media.mediaType")
    @Mapping(source = "user", target = "media.user")
    public abstract Videos toVideo(VideoDto videoDto, MediaType mediaType, User user);

    @Mapping(source = "videos.id", target = "id")
    @Mapping(source = "videos.media.url", target = "url")
    @Mapping(source = "videos.icon", target = "icon")
    @Mapping(source = "videos.name", target = "name")
    @Mapping(source = "videos.media.persistDateTime", target = "persistDateTime")
    public abstract VideoDto toDto(Videos videos);
}
