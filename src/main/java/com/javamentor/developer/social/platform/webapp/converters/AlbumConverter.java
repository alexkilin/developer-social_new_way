package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Mapper(componentModel = "spring", imports = {MediaType.class})
public abstract class AlbumConverter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mapping(target = "album.mediaType", expression = "java( MediaType.AUDIO)")
    @Mapping(target = "album.name", source = "albumDto.name")
    @Mapping(target = "album.icon", source = "albumDto.icon")
    @Mapping(target = "album.userOwnerId", source = "userId")
    public abstract AlbumAudios toAlbumAudios(AlbumDto albumDto, User userId);

    @Mapping(target = "album.mediaType", expression = "java( MediaType.IMAGE)")
    @Mapping(target = "album.name", source = "albumCreateDto.name")
    @Mapping(target = "album.icon", source = "albumCreateDto.icon")
    @Mapping(target = "album.userOwnerId", source = "albumCreateDto.userId", qualifiedByName = "userSetter")
    public abstract AlbumImage toAlbumImage(AlbumCreateDto albumCreateDto);

    @Mapping(source = "albumAudios.album.name", target = "name")
    @Mapping(source = "albumAudios.album.icon", target = "icon")
    @Mapping(source = "albumAudios.id", target = "id")
    public abstract AlbumDto toAlbumDto(AlbumAudios albumAudios);

    @Mapping(source = "albumDto.name", target = "album.name")
    @Mapping(source = "albumDto.icon", target = "album.icon")
    @Mapping(source = "userOwnerId", target = "album.userOwnerId")
    public abstract AlbumVideo toAlbumVideo(AlbumVideoDto albumDto, User userOwnerId);

    @Mapping(source = "albumVideo.album.name", target = "name")
    @Mapping(source = "albumVideo.album.icon", target = "icon")
    @Mapping(source = "albumVideo.id", target = "id")
    public abstract AlbumDto toAlbumDto(AlbumVideo albumVideo);

    @Mapping(source = "albumImage.album.name", target = "name")
    @Mapping(source = "albumImage.album.icon", target = "icon")
    @Mapping(source = "albumImage.id", target = "id")
    public abstract AlbumDto toAlbumDto(AlbumImage albumImage);

    @Named("userSetter")
    protected User userSetter(Long userId) {
        Optional<User> userOptional = userService.getById(userId);

        return userOptional.orElseThrow(() -> new EntityNotFoundException(String.format("User с id %s не существует", userId)));
    }
}
