package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class FavoriteChatConverter {

    @Autowired
    private UserService userService;

    @Mapping(source = "chatDto.title", target = "chat.title")
    @Mapping(source = "chatDto.image", target = "chat.image")
    @Mapping(source = "userId",target ="user",qualifiedByName = "userIdToUser")
    public abstract FavoriteChat chatDtoToFavoriteChat(ChatDto chatDto, Long userId);

    @Mapping(source = "chat.title", target = "title")
    @Mapping(source = "chat.image", target = "image")
    @Mapping(source = "favoriteChat.id", target = "id")
    public abstract ChatDto favoriteChatToChatDto(FavoriteChat favoriteChat);

    @Named("userIdToUser")
    public User userIdToUser(Long userId) {
        return userService.getById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User с id %s не существует", userId)));
    }
}