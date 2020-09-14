package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;

@Mapper(componentModel = "spring")
@Service
public abstract class PlaylistConverter {

    protected UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mapping(target = "ownerUser",source = "ownerUserId", qualifiedByName = "userSetter")
    public abstract Playlist toEntity(PlaylistCreateDto playlistCreateDto);

    @Named("userSetter")
    protected User userSetter(Long userId) {
        try {
            return userService.getById(userId);
        } catch (NoSuchElementException n) {
            throw new EntityNotFoundException(String.format("User с именем %s не существует", userId));
        }
    }

}
