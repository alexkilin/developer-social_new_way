package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.ImageCreateDto;
import com.javamentor.developer.social.platform.models.dto.ImageDto;
import com.javamentor.developer.social.platform.models.entity.media.Image;
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
public abstract class ImageConverter {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Mapping(target = "media.mediaType", expression = "java( MediaType.IMAGE)")
    @Mapping(target = "media.user", source = "userId", qualifiedByName = "userSetter")
    @Mapping(target = "media.url", source = "url")
    @Mapping(target = "description", source = "description")
    public abstract Image toEntity(ImageCreateDto imageCreateDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "media.url")
    @Mapping(target = "persistDateTime", source = "media.persistDateTime")
    public abstract ImageDto toImageDto(Image image);

    @Named("userSetter")
    protected User userSetter(Long userId) {
        Optional<User> userOptional = userService.getById(userId);

        return userOptional.orElseThrow(() -> new EntityNotFoundException(String.format("User с id %s не существует", userId)));
    }

}
