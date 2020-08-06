package com.javamentor.developer.social.platform.webapp.controllers.chat;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageChatDto;
import com.javamentor.developer.social.platform.models.entity.chat.Chat;
import com.javamentor.developer.social.platform.models.entity.chat.Message;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Api(value = "ChatsController")
public class ChatControllers {

    @Autowired
    private ChatDtoService chatDto;

    @Autowired
    private MessageDtoService messageDto;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/test")
            public List<String> eedede() {
        User user1 = User.builder()
                .firstName("user1")
                .lastName("user2")
                .avatar("user1")
                .build();
        User user2 = User.builder()
                .firstName("user2")
                .lastName("user2")
                .avatar("user2")
                .build();
        Chat chat1 = Chat.builder()
                .image("chat1")
                .title("chat1")
                .persistDate(LocalDateTime.now())
                .build();
        Chat chat2 = Chat.builder()
                .image("chat1")
                .persistDate(LocalDateTime.now())
                .title("chat1")
                .build();
        Message message2 = Message.builder()
                .message("message2")
                .userSender(user2)
                .lastRedactionDate(LocalDateTime.now())
                .is_unread(true)
                .media(new HashSet<>())
                .build();
        Message message1 = Message.builder()
                .message("message1")
                .userSender(user1)
                .lastRedactionDate(LocalDateTime.now())
                .is_unread(true)
                .media(new HashSet<>())
                .build();
        Set<Message> messagesSet = new HashSet<>();
        messagesSet.add(message2);
        messagesSet.add(message1);
        chat1.setMessages(messagesSet);
        chat2.setMessages(messagesSet);
        Set<Chat> chatSet = new HashSet<>();
        chatSet.add(chat1);
        chatSet.add(chat2);
        message1.setChat(chatSet);
        message2.setChat(chatSet);
        user1.setChats(chatSet);
        user2.setChats(chatSet);
        Set<User> userSet = new HashSet<>();
        userSet.add(user1);
        userSet.add(user2);
        chat1.setUsers(userSet);
        chat2.setUsers(userSet);
        userService.create(user2);
        userService.create(user1);


        List<String> stringList = new ArrayList<>();
        for (ChatDto chat:chatDto.getAllChatDtoByUserId(user1.getUserId())){
            stringList.add(chat.getLastMessage());
        }
        messageDto.getAllMessageDtoByChatId(user2.getUserId());
        return stringList;
    }

    @GetMapping("/api/user/{userId}/chats")
    @ApiOperation(value = "Список чатов", response = ChatDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code =404, message = "404 error")
    })
    public ResponseEntity<List<ChatDto>> getChatsDto(@PathVariable String userId){
        return ResponseEntity.ok(chatDto.getAllChatDtoByUserId(Long.parseLong(userId)));
    }

    @GetMapping("/api/user/{chatId}/messages")
    @ApiOperation(value = "Список сообщений", response = MessageChatDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code =404, message = "404 error")
    })
    public ResponseEntity<List<MessageChatDto>> getMessageDto(@PathVariable String chatId){
        return ResponseEntity.ok(messageDto.getAllMessageDtoByChatId(Long.parseLong(chatId)));
    }
}
