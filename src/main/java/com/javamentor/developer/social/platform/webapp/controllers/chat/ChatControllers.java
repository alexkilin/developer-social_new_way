package com.javamentor.developer.social.platform.webapp.controllers.chat;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "ApiAllChatsUser", description = "Получение всех чатов пользователя,и получение всех сообщений чата.")
public class ChatControllers {
    private final ChatDtoService chatDto;
    private final MessageDtoService messageDto;
    private final UserService userService;
    private final SingleChatService chatService;

    @Autowired
    public ChatControllers(ChatDtoService chatDtoService, MessageDtoService messageDtoService,
                           UserService userService, SingleChatService chatService) {
        this.userService = userService;
        this.chatDto = chatDtoService;
        this.messageDto = messageDtoService;
        this.chatService = chatService;
    }

    @GetMapping("/api/user/chats")
    @ApiOperation(value = "Список чатов юзера.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = ChatDto.class),
            @ApiResponse(code = 404, message = "404 error"),

    })
    public ResponseEntity<List<ChatDto>> getChatsDto() {
        return ResponseEntity.ok(chatDto.getAllChatDtoByUserId(60L));
    }

    @GetMapping("/api/user/groupChats/{chatId}/messages")
    @ApiOperation(value = "Список сообщений группового чата по Id чата.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = MessageDto.class),
            @ApiResponse(code = 404, message = "404 error")
    })
    public ResponseEntity<List<MessageDto>> getAllMessageDtoByGroupChatId(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageDto.getAllMessageDtoFromGroupChatByChatId(chatId));
    }

    @GetMapping("/api/user/singleChats/{chatId}/messages")
    @ApiOperation(value = "Список сообщений одиночного чата по Id чата.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = MessageDto.class),
            @ApiResponse(code = 404, message = "404 error")
    })
    public ResponseEntity<List<MessageDto>> getAllMessageDtoBySingleChatId(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageDto.getAllMessageDtoFromSingleChatByChatId(chatId));
    }

    @PostMapping("api/chat/{chatId}/user/{userId}/delete")
    @ApiOperation(value = "Удаление пользователя из single chat.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 OK"),
            @ApiResponse(code = 400, message = "400 Bad Request")
    })
    public ResponseEntity<?> deleteUserFromSingleChat(
            @PathVariable("chatId") Long chatId,
            @PathVariable("userId") Long userId) {
        SingleChat singleChat = chatService.getById(chatId);
        if (singleChat == null) {
            return new ResponseEntity<>("chat not found", HttpStatus.BAD_REQUEST);
        }
        if (!SingleChat.deleteUserFromSingleChat(singleChat, userService, userId)){
            return new ResponseEntity<>("user has not found chat", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("done delete chat from user", HttpStatus.OK);
    }

}
