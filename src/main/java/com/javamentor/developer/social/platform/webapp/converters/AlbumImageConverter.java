package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.AlbumImageDTO;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring", imports = {MediaType.class})
public abstract class AlbumImageConverter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mappings({
            @Mapping(source = "albumImageDTO.id", target = "id"),
            @Mapping(target = "album.mediaType", expression = "java(MediaType.IMAGE)"),
            @Mapping(source = "albumImageDTO.id", target = "album.id"),
            @Mapping(source = "albumImageDTO.name", target = "album.name"),
            @Mapping(source = "albumImageDTO.icon", target = "album.icon"),
            @Mapping(source = "userOwner.userId", target = "album.userOwnerId", qualifiedByName = "userSetter")
    })
    public abstract AlbumImage toAlbumImage(AlbumImageDTO albumImageDTO, User userOwner);

    @Mappings({
            @Mapping(source = "albumImage.id", target = "id"),
            @Mapping(source = "albumImage.album.icon", target = "icon"),
            @Mapping(source = "albumImage.album.name", target = "name"),
            @Mapping(source = "albumImage.album.persistDate", target = "persistDate"),
            @Mapping(source = "albumImage.album.lastRedactionDate", target = "lastRedactionDate")
    })
    public abstract AlbumImageDTO toAlbumImageDto(AlbumImage albumImage);

    // TODO : Убрать try catch
    @Named("userSetter")
    User userSetter(Long userId) {
        try {
            return userService.getById(userId);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException(String.format("User с id %s не существует", userId));
        }
    }
}
