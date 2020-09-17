package com.javamentor.developer.social.platform.webapp.controllers.chat;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.ChatEditTitleDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.developer.social.platform.webapp.converters.GroupChatConverter;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/user")
@Api(value = "ApiAllChatsUser", description = "Получение всех чатов пользователя,и получение всех сообщений чата.")
public class ChatControllers {
    private final ChatDtoService chatDtoService;
    private final MessageDtoService messageDtoService;
    private final GroupChatService groupChatService;
    private final SingleChatService singleChatService;
    private  GroupChatConverter groupChatConverter;
    private final UserService userService;


    public ChatControllers(ChatDtoService chatDtoService, MessageDtoService messageDtoService,
                GroupChatService groupChatService, SingleChatService singleChatService,GroupChatConverter groupChatConverter,
                UserService userService) {
            this.chatDtoService = chatDtoService;
            this.messageDtoService = messageDtoService;
            this.groupChatService = groupChatService;
            this.singleChatService = singleChatService;
            this.groupChatConverter = groupChatConverter;
            this.userService = userService;
        }

        @GetMapping("/chats")
        @ApiOperation(value = "Список чатов юзера.")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = ChatDto.class)
        })
        public ResponseEntity<List<ChatDto>> getChatsDto () {
            return ResponseEntity.ok(chatDtoService.getAllChatDtoByUserId(60L));
        }

        @GetMapping("/groupChats/{chatId}/messages")
        @ApiOperation(value = "Список сообщений группового чата по Id чата.")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = MessageDto.class),
                @ApiResponse(code = 404, message = "Чат с данным Id не существует", response = String.class)
        })
        public ResponseEntity<?> getAllMessageDtoByGroupChatId
        (@ApiParam(value = "Id группового чата") @PathVariable Long chatId){
            if (!groupChatService.existById(chatId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Чат с id %s не найден.", chatId));
            }
            return ResponseEntity.ok(messageDtoService.getAllMessageDtoFromGroupChatByChatId(chatId));
        }

        @GetMapping("/singleChats/{chatId}/messages")
        @ApiOperation(value = "Список сообщений одиночного чата по Id чата.")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = MessageDto.class),
                @ApiResponse(code = 404, message = "Чат с данным Id не существует", response = String.class)
        })
        public ResponseEntity<?> getAllMessageDtoBySingleChatId (@ApiParam(value = "Id чата") @PathVariable Long chatId)
        {
            if (!singleChatService.existById(chatId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Чат с id %s не найден.", chatId));
            }
            return ResponseEntity.ok(messageDtoService.getAllMessageDtoFromSingleChatByChatId(chatId));
        }

        @PutMapping("/chat/group/edit/title")
        @ApiOperation(value = "Изменение title группового чата.")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK", response = MessageDto.class),
                @ApiResponse(code = 404, message = "Чат с данным Id не существует", response = String.class)
        })
        public ResponseEntity<?> editGroupChatTitle
        (@ApiParam(value = "Объект чата") @RequestBody @NotNull @Valid ChatEditTitleDto chatEditTitleDto){
            Long chatId = chatEditTitleDto.getId();
            if (!groupChatService.existById(chatId)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Чат с id %s не найден.", chatId));
            }
            GroupChat groupChat = groupChatService.getById(chatId);
            groupChat.setTitle(chatEditTitleDto.getTitle());
            groupChatService.update(groupChat);
            return ResponseEntity.ok().body(chatDtoService.getChatDtoByGroupChatId(chatId));
        }

        @PostMapping("/{chatId}/user/{userId}/delete")
        @ApiOperation(value = "Удаление пользователя из single chat.")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "200 OK"),
                @ApiResponse(code = 400, message = "400 Bad Request")
        })
        public ResponseEntity<?> deleteUserFromSingleChat (
                @PathVariable("chatId") Long chatId,
                @PathVariable("userId") Long userId){
            SingleChat singleChat = singleChatService.getById(chatId);
            User user = userService.getById(userId);
            if (singleChat == null) {
                return ResponseEntity.badRequest().body("chat not found");
            } else if (user == null) {
                return ResponseEntity.badRequest().body("user not found");
            }
            if (!singleChatService.deleteUserFromSingleChat(singleChat, userId)) {
                return ResponseEntity.badRequest().body("user has not found chat");
            }
            return ResponseEntity.ok("done delete chat from user");
        }

        @PostMapping("/chat/group/create")
        @ApiOperation(value = "Создание группового чата")
        @ApiResponses(value = {
                @ApiResponse(code = 200, message = "OK", response = ChatDto.class),
                @ApiResponse(code = 404, message = "404 error")
        })
        public ResponseEntity<ChatDto> createGroupChat (@RequestBody @NotNull @Valid ChatDto chatDto){

            GroupChat groupChat  = groupChatConverter.chatToGroupChat(chatDto, 3L);
            groupChatService.create(groupChat);
            ChatDto outputChatDto = groupChatConverter.groupChatToChatDto(groupChat);
            return ResponseEntity.ok(outputChatDto);
        }
    }



