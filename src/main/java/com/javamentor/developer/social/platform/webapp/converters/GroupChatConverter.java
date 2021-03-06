package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class GroupChatConverter {

    @Autowired
    private UserService userService;

    @Mapping(source = "chatDto.title", target = "chat.title")
    @Mapping(source = "chatDto.image", target = "chat.image")
    @Mapping(source = "userId",target ="users",qualifiedByName = "userIdToSet")
    public abstract GroupChat chatToGroupChat(ChatDto chatDto,Long userId);


    @Mapping(source = "chat.title", target = "title")
    @Mapping(source = "chat.image", target = "image")
    @Mapping(source = "groupChat.id", target = "id")
    public abstract ChatDto groupChatToChatDto(GroupChat groupChat);

    @Named("userIdToSet")
    public  Set<User> userIdToSet(Long userId) {
        User user = userService.getById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User с id %s не существует", userId)));

        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        return userSet;
    }




}
