package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class AlbumConverter {

    @Mapping(source = "albumDto.name", target = "album.name")
    @Mapping(source = "albumDto.icon", target = "album.icon")
    @Mapping(source = "userOwnerId", target = "album.userOwnerId")
    public abstract AlbumAudios toAlbumAudios(AlbumDto albumDto, User userOwnerId);

    @Mapping(source = "albumAudios.album.name", target = "name")
    @Mapping(source = "albumAudios.album.icon", target = "icon")
    @Mapping(source = "albumAudios.id", target = "id")
    public abstract AlbumDto toAlbumDto(AlbumAudios albumAudios);
}
