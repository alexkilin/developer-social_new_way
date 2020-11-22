package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
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

    @Named("userSetter")
    protected User userSetter(Long userId) {
        Optional<User> userOptional = userService.getById(userId);

        return userOptional.orElseThrow(() -> new EntityNotFoundException(String.format("User с id %s не существует", userId)));
    }
}
