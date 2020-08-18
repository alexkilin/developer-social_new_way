package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.entity.chat.Chat;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.ChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/api/test", produces = "application/json")
public class TestController {

    ChatService chatService;

    @Autowired
    public TestController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable @NotNull Long id){
        return ResponseEntity.ok(chatService.getById(id));
    }
}
