package com.javamentor.developer.social.platform.webapp.controllers.chat;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.developer.social.platform.webapp.converters.GroupChatConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/user")
@Api(value = "ApiAllChatsUser", description = "Получение всех чатов пользователя,и получение всех сообщений чата.")
public class ChatControllers {
    private final ChatDtoService chatDto;
    private final MessageDtoService messageDto;
    private final GroupChatConverter groupChatConverter;
    private GroupChatService groupChatService;

    public ChatControllers(ChatDtoService chatDtoService, MessageDtoService messageDtoService,GroupChatService groupChatService,GroupChatConverter groupChatConverter) {
        this.chatDto = chatDtoService;
        this.messageDto = messageDtoService;
        this.groupChatService = groupChatService;
        this.groupChatConverter = groupChatConverter;
    }

    @GetMapping("/chats")
    @ApiOperation(value = "Список чатов юзера.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",responseContainer = "List",response = ChatDto.class)
    })
    public ResponseEntity<List<ChatDto>> getChatsDto(){
        return ResponseEntity.ok(chatDto.getAllChatDtoByUserId(60L));
    }

    @GetMapping("/groupChats/{chatId}/messages")
    @ApiOperation(value = "Список сообщений группового чата по Id чата.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",responseContainer = "List", response = MessageDto.class),
            @ApiResponse(code = 400, message = "error")
    })
    public ResponseEntity<List<MessageDto>> getAllMessageDtoByGroupChatId(@PathVariable Long chatId){
        return ResponseEntity.ok(messageDto.getAllMessageDtoFromGroupChatByChatId(chatId));
    }

    @GetMapping("/singleChats/{chatId}/messages")
    @ApiOperation(value = "Список сообщений одиночного чата по Id чата.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",responseContainer = "List", response = MessageDto.class),
            @ApiResponse(code = 404, message = "404 error")
    })
    public ResponseEntity<List<MessageDto>> getAllMessageDtoBySingleChatId(@PathVariable Long chatId){
        return ResponseEntity.ok(messageDto.getAllMessageDtoFromSingleChatByChatId(chatId));
    }
    @PostMapping("/chat/group/create")
    @ApiOperation(value = "Создание группового чата")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChatDto.class),
            @ApiResponse(code = 404, message = "404 error")
    })
    public ResponseEntity<ChatDto> createGroupChat(@RequestBody @Valid @NonNull ChatDto chatDto) {

        GroupChat groupChat = groupChatConverter.chatToGroupChat(chatDto,60L);

        groupChatService.update(groupChat);

        ChatDto outputChatDto =groupChatConverter.groupChatToChatDto(groupChat);
        return ResponseEntity.ok(outputChatDto);


    }



}
