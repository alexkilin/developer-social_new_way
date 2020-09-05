package com.javamentor.developer.social.platform.webapp.controllers.chat;

import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("api/chat")
@Api(value = "ApiSingleChat", description = "Удаление пользователя из SingleChat.")
public class ChatControllerApiChat {

    private final SingleChatService singleChatService;

    public ChatControllerApiChat(SingleChatService singleChatService) {
        this.singleChatService = singleChatService;
    }

    @PostMapping("/{chatId}/user/{userId}/delete")
    @ApiOperation(value = "Удаление пользователя из single chat.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 OK"),
            @ApiResponse(code = 400, message = "400 Bad Request")
    })
    public ResponseEntity<?> deleteUserFromSingleChat(
            @PathVariable("chatId") Long chatId,
            @PathVariable("userId") Long userId) {
        SingleChat singleChat = singleChatService.getById(chatId);
        if (singleChat == null) {
            return new ResponseEntity<>("chat not found", HttpStatus.BAD_REQUEST);
        }
        if (!singleChatService.deleteUserFromSingleChat(singleChat, userId)){
            return new ResponseEntity<>("user has not found chat", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("done delete chat from user", HttpStatus.OK);
    }

}
