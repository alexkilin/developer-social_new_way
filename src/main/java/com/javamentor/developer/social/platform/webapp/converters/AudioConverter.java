package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public abstract class AudioConverter {


    @Mapping(source = "audioDto.url", target = "media.url")
    @Mapping(source = "audioDto.icon", target = "icon")
    @Mapping(source = "audioDto.name", target = "name")
    @Mapping(source = "audioDto.author", target = "author")
    @Mapping(source = "audioDto.album", target = "album")
    @Mapping(source = "mediaType", target = "media.mediaType")
    @Mapping(source = "user", target = "media.user")
    public abstract Audios toAudio(AudioDto audioDto, MediaType mediaType, User user);
}
