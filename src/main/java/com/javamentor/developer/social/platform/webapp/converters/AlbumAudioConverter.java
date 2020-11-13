package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {MediaType.class})
public abstract class AlbumAudioConverter {

    @Mappings({
            @Mapping(target = "album.mediaType", expression = "java( MediaType.AUDIO)"),
            @Mapping(target = "album.name", source = "albumDto.name"),
            @Mapping(target = "album.icon", source = "albumDto.icon"),
            @Mapping(target = "album.userOwnerId", source = "userId")
    })
    public abstract AlbumAudios toAlbumAudios(AlbumDto albumDto, User userId);

    @Mapping(source = "albumAudios.album.name", target = "name")
    @Mapping(source = "albumAudios.album.icon", target = "icon")
    @Mapping(source = "albumAudios.id", target = "id")
    public abstract AlbumAudioDto toAlbumAudioDto(AlbumAudios albumAudios);
}
