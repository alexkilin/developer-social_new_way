package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
public abstract class SingleChatConverter {

    @Autowired
    private UserService userService;

    @Mapping(source = "chatDto.title", target = "title")
    @Mapping(source = "chatDto.image", target = "image")
    @Mapping(source = "userOneId", target ="userOne", qualifiedByName = "userIdToUser")
    @Mapping(source = "userTwoId", target ="userTwo", qualifiedByName = "userIdToUser")
    public abstract SingleChat chatToSingleChat(ChatDto chatDto, Long userOneId, Long userTwoId);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "id", target = "id")
    public abstract ChatDto singleChatToChatDto(SingleChat singleChat);

    @Named("userIdToUser")
    public User userIdToUser(Long userId) {

        return userService.getById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User с id %s не существует", userId)));
    }
}
