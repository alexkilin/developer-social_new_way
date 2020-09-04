package com.javamentor.developer.social.platform.webapp.controllers.chat;
import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/user")
@Api(value = "ApiAllChatsUser", description = "Получение всех чатов пользователя,и получение всех сообщений чата.")
public class ChatControllers {
    private final ChatDtoService chatDto;
    private final MessageDtoService messageDto;

    public ChatControllers(ChatDtoService chatDtoService, MessageDtoService messageDtoService){
        this.chatDto = chatDtoService;
        this.messageDto = messageDtoService;
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

}
