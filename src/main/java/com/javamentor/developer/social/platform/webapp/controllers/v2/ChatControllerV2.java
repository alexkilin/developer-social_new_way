package com.javamentor.developer.social.platform.webapp.controllers.v2;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.ChatEditTitleDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.GroupChatConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/api/v2/chats", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "ChatApi-v2", description = "Операции над чатами")
public class ChatControllerV2 {
    private final ChatDtoService chatDtoService;
    private final MessageDtoService messageDtoService;
    private final GroupChatService groupChatService;
    private final SingleChatService singleChatService;
    private final GroupChatConverter groupChatConverter;
    private final UserService userService;


    public ChatControllerV2(ChatDtoService chatDtoService, MessageDtoService messageDtoService,
                            GroupChatService groupChatService, SingleChatService singleChatService, GroupChatConverter groupChatConverter,
                            UserService userService) {
        this.chatDtoService = chatDtoService;
        this.messageDtoService = messageDtoService;
        this.groupChatService = groupChatService;
        this.singleChatService = singleChatService;
        this.groupChatConverter = groupChatConverter;
        this.userService = userService;
    }

    @GetMapping("/user/{userId}/chats")
    @ApiOperation(value = "Список чатов юзера.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = ChatDto.class)
    })
    public ResponseEntity<List<ChatDto>> getChatsDto(@ApiParam(value = "Id юзера", example = "60") @PathVariable("userId") @NonNull Long userId) {
        return ResponseEntity.ok(chatDtoService.getAllChatDtoByUserId(userId));
    }

    @GetMapping("/group-chats/{chatId}/messages")
    @ApiOperation(value = "Список сообщений группового чата по Id чата.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = MessageDto.class),
            @ApiResponse(code = 404, message = "Чат с данным Id не существует", response = String.class)
    })
    public ResponseEntity<?> getAllMessageDtoByGroupChatId
            (@ApiParam(value = "Id группового чата") @PathVariable Long chatId) {
        if (!groupChatService.existById(chatId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Chat id %s not found", chatId));
        }
        return ResponseEntity.ok(messageDtoService.getAllMessageDtoFromGroupChatByChatId(chatId));
    }

    @GetMapping("/single-chats/{chatId}/messages")
    @ApiOperation(value = "Список сообщений одиночного чата по Id чата.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = MessageDto.class),
            @ApiResponse(code = 404, message = "Чат с данным Id не существует", response = String.class)
    })
    public ResponseEntity<?> getAllMessageDtoBySingleChatId(@ApiParam(value = "Id чата") @PathVariable Long chatId) {
        if (!singleChatService.existById(chatId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Chat id %s not found", chatId));
        }
        return ResponseEntity.ok(messageDtoService.getAllMessageDtoFromSingleChatByChatId(chatId));
    }

    @PutMapping("/group-chats/title")
    @ApiOperation(value = "Изменение title группового чата.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MessageDto.class),
            @ApiResponse(code = 404, message = "Чат с данным Id не существует", response = String.class)
    })
    public ResponseEntity<?> editGroupChatTitle(
            @ApiParam(value = "Объект чата") @RequestBody @NotNull @Valid ChatEditTitleDto chatEditTitleDto) {
        Long chatId = chatEditTitleDto.getId();

        Optional<GroupChat> result = groupChatService.getById(chatId);
        if (Objects.nonNull(result) && result.isPresent()) {
            GroupChat groupChat = result.get();
            groupChat.setTitle(chatEditTitleDto.getTitle());
            groupChatService.update(groupChat);
            return ResponseEntity.ok().body(chatDtoService.getChatDtoByGroupChatId(chatId));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Chat id %s not found", chatId));
        }
    }

    @DeleteMapping("/single-chats/{chatId}/user/{userId}")
    @ApiOperation(value = "Удаление пользователя из single chat.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "200 OK"),
            @ApiResponse(code = 400, message = "400 Bad Request")
    })
    public ResponseEntity<?> deleteUserFromSingleChat(@PathVariable("chatId") Long chatId,
                                                      @PathVariable("userId") Long userId) {
        Optional<SingleChat> singleChatOptional = singleChatService.getById(chatId);
        Optional<User> userOptional = userService.getById(userId);
        if (!singleChatOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Single chat not found");
        }
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("user not found");
        }
        if (!singleChatService.deleteUserFromSingleChat(singleChatOptional.get(), userId)) {
            return ResponseEntity.badRequest().body("No such user in chat");
        }
        return ResponseEntity.ok("done delete chat from user");
    }

    @PostMapping("/group-chats")
    @ApiOperation(value = "Создание группового чата")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChatDto.class),
            @ApiResponse(code = 404, message = "404 error")
    })
    @Validated(OnCreate.class)
    public ResponseEntity<?> createGroupChat(@RequestBody @NotNull @Valid ChatDto chatDto) {

        GroupChat groupChat = groupChatConverter.chatToGroupChat(chatDto, 3L);
        groupChatService.create(groupChat);
        ChatDto outputChatDto = groupChatConverter.groupChatToChatDto(groupChat);
        return ResponseEntity.ok(outputChatDto);
    }
}



