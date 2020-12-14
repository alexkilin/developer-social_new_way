package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Mapper(componentModel = "spring")
@Service
public abstract class MessageConverter {

    @Autowired
    private UserService userService;

    @Mapping(source = "messageDto.persistDate", target = "persistDate")
    @Mapping(source = "messageDto.message", target = "message")
    @Mapping(source = "messageDto.mediaDto", target = "media")
    @Mapping(source = "userId", target ="userSender", qualifiedByName = "userIdToUser")
    public abstract Message messageDtoToMessage(MessageDto messageDto, Long userId);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "persistDate", target = "persistDate")
    @Mapping(source = "media", target = "mediaDto")
    public abstract MessageDto messageToMessageDto(Message message);

    @Named("userIdToUser")
    public User userIdToUser(Long userId) {

        return userService.getById(userId).orElseThrow(
                () -> new EntityNotFoundException(String.format("User с id %s не существует", userId)));
    }
}
