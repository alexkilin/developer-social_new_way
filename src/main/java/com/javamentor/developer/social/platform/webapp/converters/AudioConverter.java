package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
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

    @Mapping(source = "audios.id", target = "id")
    @Mapping(source = "audios.media.url", target = "url")
    @Mapping(source = "audios.icon", target = "icon")
    @Mapping(source = "audios.name", target = "name")
    @Mapping(source = "audios.author", target = "author")
    @Mapping(source = "audios.album", target = "album")
    @Mapping(source = "audios.media.persistDateTime", target = "persistDateTime")
    public abstract AudioDto toDto(Audios audios);

}
