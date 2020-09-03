package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class SendMessage {
    private MessageService messageService;

    @Autowired
    public SendMessage(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/chat.yml.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getUserSender());
        return message;
    }
}
