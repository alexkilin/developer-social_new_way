package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.MessageService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class SendMessage {


    private final UserService userService;
    private final MessageConverter messageConverter;
    private final MessageService messageService;

    @Autowired
    public SendMessage(UserService userService,
                       MessageService messageService,
                       MessageConverter messageConverter) {
        this.userService = userService;
        this.messageService = messageService;
        this.messageConverter = messageConverter;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(@Payload MessageDto messageDto) {
        User user = userService.getPrincipal();
        Message message = messageConverter.messageDtoToMessage(messageDto, user.getUserId());
        messageService.create(message);
        return messageConverter.messageToMessageDto(message);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public MessageDto addUser(@Payload MessageDto messageDto,
                              SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", userService.getPrincipal());
        return messageDto;
    }
}
