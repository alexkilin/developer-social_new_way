package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.AlbumCreateDto;
import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring", imports = {MediaType.class})
public abstract class AlbumConverter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mapping(target = "album.mediaType", expression = "java( MediaType.IMAGE)")
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

    @Mapping(source = "albumImage.album.name", target = "name")
    @Mapping(source = "albumImage.album.icon", target = "icon")
    @Mapping(source = "albumImage.id", target = "id")
    public abstract AlbumDto toAlbumDto(AlbumImage albumImage);

    @Named("userSetter")
    protected User userSetter(Long userId) {
        try {
            return userService.getById(userId);
        } catch (NoSuchElementException n) {
            throw new EntityNotFoundException(String.format("User с id %s не существует", userId));
        }
    }
}
