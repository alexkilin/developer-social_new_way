package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", imports = {MediaType.class})
public abstract class AlbumVideoConverter {

    @Mappings( {
            @Mapping(source = "albumDto.name", target = "album.name"),
            @Mapping(source = "albumDto.icon", target = "album.icon"),
            @Mapping(source = "userOwnerId", target = "album.userOwnerId")
    })
    public abstract AlbumVideo toAlbumVideo(AlbumVideoDto albumDto, User userOwnerId);

    @Mappings({
            @Mapping(source = "albumVideo.album.name", target = "name"),
            @Mapping(source = "albumVideo.album.icon", target = "icon"),
            @Mapping(source = "albumVideo.id", target = "id")
    })
    public abstract AlbumDto toAlbumVideoDto(AlbumVideo albumVideo);
}
