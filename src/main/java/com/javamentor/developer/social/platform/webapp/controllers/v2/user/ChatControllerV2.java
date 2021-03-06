package com.javamentor.developer.social.platform.webapp.controllers.v2.user;

import com.javamentor.developer.social.platform.models.dto.chat.ChatDto;
import com.javamentor.developer.social.platform.models.dto.chat.ChatEditTitleDto;
import com.javamentor.developer.social.platform.models.dto.chat.MessageDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.entity.chat.Chat;
import com.javamentor.developer.social.platform.models.entity.chat.FavoriteChat;
import com.javamentor.developer.social.platform.models.entity.chat.GroupChat;
import com.javamentor.developer.social.platform.models.entity.chat.SingleChat;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.security.util.SecurityHelper;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.ChatDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.FavoriteChatService;
import com.javamentor.developer.social.platform.service.abstracts.dto.chat.MessageDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.GroupChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.chat.SingleChatService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.GroupChatConverter;
import com.javamentor.developer.social.platform.webapp.converters.SingleChatConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@Validated
@RestController
@RequestMapping(value = "/api/v2/chats", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "ChatApi-v2", description = "???????????????? ?????? ????????????")
public class ChatControllerV2 {

    private final ChatDtoService chatDtoService;
    private final MessageDtoService messageDtoService;
    private final GroupChatService groupChatService;
    private final SingleChatService singleChatService;
    private final GroupChatConverter groupChatConverter;
    private final UserService userService;
    private final SecurityHelper securityHelper;
    private final SingleChatConverter singleChatConverter;
    private final FavoriteChatService favoriteChatService;

    @Autowired
    public ChatControllerV2(ChatDtoService chatDtoService, MessageDtoService messageDtoService,
                            GroupChatService groupChatService, SingleChatService singleChatService, GroupChatConverter groupChatConverter,
                            UserService userService, SecurityHelper securityHelper, SingleChatConverter singleChatConverter,
                            FavoriteChatService favoriteChatService) {
        this.chatDtoService = chatDtoService;
        this.messageDtoService = messageDtoService;
        this.groupChatService = groupChatService;
        this.singleChatService = singleChatService;
        this.groupChatConverter = groupChatConverter;
        this.userService = userService;
        this.securityHelper = securityHelper;
        this.singleChatConverter = singleChatConverter;
        this.favoriteChatService = favoriteChatService;
    }

    @GetMapping("/user/{userId}/chats")
    @ApiOperation(value = "???????????? ?????????? ??????????.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = ChatDto.class)
    })
    public ResponseEntity<List<ChatDto>> getChatsDto(@ApiParam(value = "Id ??????????", example = "60") @PathVariable("userId") @NonNull Long userId) {
        return ResponseEntity.ok(chatDtoService.getAllChatDtoByUserId(userId));
    }

    @GetMapping(value = "/user/chats", params = {"currentPage", "itemsOnPage", "search"})
    @ApiOperation(value = "?????????? ???? ??????????.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = PageDto.class)
    })
    public ResponseEntity<PageDto<ChatDto, ?>> getChatsByChatName(
            @ApiParam(value = "?????? ????????, ?????????? ???? ????????????, ???? ?????????????? ???? ????????????????", example = "???????????? ??????????") @RequestParam String search,
            @ApiParam(value = "?????????????? ????????????????", example = "1") @RequestParam("currentPage") int currentPage,
            @ApiParam(value = "???????????????????? ???????????? ???? ????????????????", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        Long userPrincipalId = securityHelper.getPrincipal().getUserId();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userPrincipalId", userPrincipalId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        parameters.put("search", search);
        return ResponseEntity.ok(chatDtoService.getChatDtoByChatName(parameters));
    }

    @GetMapping(value = "/group-chats/{chatId}/messages", params = {"currentPage", "itemsOnPage"})
    @ApiOperation(value = "???????????? ?????????????????? ???????????????????? ???????? ???? Id ????????.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = MessageDto.class),
            @ApiResponse(code = 404, message = "?????? ?? ???????????? Id ???? ????????????????????", response = String.class)
    })
    public ResponseEntity<?> getAllMessageDtoByGroupChatId
            (@ApiParam(value = "Id ???????????????????? ????????") @PathVariable Long chatId,
             @ApiParam(value = "?????????????? ????????????????", example = "1") @RequestParam("currentPage") int currentPage,
             @ApiParam(value = "???????????????????? ???????????? ???? ????????????????", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        if (!groupChatService.existById(chatId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Chat id %s not found", chatId));
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("chatId", chatId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok(messageDtoService.getAllMessageDtoFromGroupChatByChatId(parameters));
    }

    @GetMapping(value = "/single-chats/{chatId}/messages", params = {"currentPage", "itemsOnPage"})
    @ApiOperation(value = "???????????? ?????????????????? ???????????????????? ???????? ???? Id ????????.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = MessageDto.class),
            @ApiResponse(code = 404, message = "?????? ?? ???????????? Id ???? ????????????????????", response = String.class)
    })
    public ResponseEntity<?> getAllMessageDtoBySingleChatId(@ApiParam(value = "Id ????????") @PathVariable Long chatId,
                                                            @ApiParam(value = "?????????????? ????????????????", example = "1") @RequestParam("currentPage") int currentPage,
                                                            @ApiParam(value = "???????????????????? ???????????? ???? ????????????????", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {
        if (!singleChatService.existById(chatId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Chat id %s not found", chatId));
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("chatId", chatId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok(messageDtoService.getAllMessageDtoFromSingleChatByChatId(parameters));
    }

    @PutMapping("/group-chats/title")
    @ApiOperation(value = "?????????????????? title ???????????????????? ????????.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MessageDto.class),
            @ApiResponse(code = 404, message = "?????? ?? ???????????? Id ???? ????????????????????", response = String.class)
    })
    public ResponseEntity<?> editGroupChatTitle(@ApiParam(value = "???????????? ????????")
                                                @RequestBody @NotNull @Valid ChatEditTitleDto chatEditTitleDto) {
        Long chatId = chatEditTitleDto.getId();

        Optional<GroupChat> result = groupChatService.getByIdWithChat(chatId);

        if (result.isPresent()) {
            GroupChat groupChat = result.get();
            groupChat.getChat().setTitle(chatEditTitleDto.getTitle());
            groupChatService.update(groupChat);
        }
        return ResponseEntity.ok().body(chatDtoService.getChatDtoByGroupChatId(chatId));
    }

    @DeleteMapping("/single-chats/{chatId}/user/{userId}")
    @ApiOperation(value = "???????????????? ???????????????????????? ???? single chat.")
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
    @ApiOperation(value = "???????????????? ???????????????????? ????????")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChatDto.class),
            @ApiResponse(code = 404, message = "404 error")
    })
    @Validated(OnCreate.class)
    public ResponseEntity<?> createGroupChat(@RequestBody @NotNull @Valid ChatDto chatDto) {
        User user = securityHelper.getPrincipal();
        if (Objects.isNull(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        GroupChat groupChat = groupChatConverter.chatToGroupChat(chatDto, user.getUserId());
        groupChatService.create(groupChat);
        ChatDto outputChatDto = groupChatConverter.groupChatToChatDto(groupChat);
        return ResponseEntity.ok(outputChatDto);
    }

    @PostMapping("/single-chats/user/{userId}")
    @ApiOperation(value = "???????????????? ???????????????????? ????????")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChatDto.class),
            @ApiResponse(code = 404, message = "404 error")
    })
    @Validated(OnCreate.class)
    public ResponseEntity<?> createSingleChat(@RequestBody @NotNull @Valid ChatDto chatDto,
                                              @PathVariable("userId") Long userId) {
        User userOne = securityHelper.getPrincipal();
        if (Objects.isNull(userOne)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        Optional<User> optionalUser = userService.getById(userId);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with id: %s not found", userId));
        }
        SingleChat singleChat = singleChatConverter.chatDtoToSingleChat(chatDto, userOne.getUserId(), userId);
        singleChatService.create(singleChat);
        ChatDto outputChatDto = singleChatConverter.singleChatToChatDto(singleChat);
        return ResponseEntity.ok(outputChatDto);
    }

    @GetMapping("/user/chats/favorite")
    @ApiOperation(value = "?????????????????? ???????????? ?????????? ??????????.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = ChatDto.class)
    })
    public ResponseEntity<List<ChatDto>> getAllFavoriteChatDto() {
        Long userPrincipalId = securityHelper.getPrincipal().getUserId();
        return ResponseEntity.ok(chatDtoService.getAllFavoriteChatDto(userPrincipalId));
    }

    @PostMapping("/user/chat/{chatId}/favorite")
    @ApiOperation(value = "???????????????????? ???????? ?? ??????????????????.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChatDto.class),
            @ApiResponse(code = 404, message = "404 error")
    })
    @Validated(OnCreate.class)
    public ResponseEntity<?> addChatToFavorites(@PathVariable(value = "chatId") Long chatId) {
        User user = securityHelper.getPrincipal();
        Optional<FavoriteChat> optionalFavoriteChat = favoriteChatService.getFavoriteByChatIdAndUserId(chatId, user.getUserId());
        if(optionalFavoriteChat.isPresent()) {
            return ResponseEntity.badRequest().body("The Chat has already been added to the Favorite");
        }
        Chat chat = Chat.builder().id(chatId).build();
        FavoriteChat favoriteChat = FavoriteChat.builder().user(user).chat(chat).build();
        favoriteChatService.create(favoriteChat);
        ChatDto chatDto = ChatDto.builder().id(chatId).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(chatDto);
    }

    @PatchMapping(value = "/group-chats/{chatId}/changeTitle", params = {"newTitle"})
    @ApiOperation(value = "?????????????????? ???????????????? ???????????????????? ????????.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChatDto.class),
            @ApiResponse(code = 404, message = "?????? ?? ???????????? Id ???? ????????????????????", response = String.class),
            @ApiResponse(code = 400, message = "?????????? ???????????????? ???????? ????????????")
    })
    public ResponseEntity<?> changeGroupChatTitle(@ApiParam(value = "Id ???????????????????? ????????") @PathVariable Long chatId,
                                                  @ApiParam(value = "?????????? ???????????????? ?????? ????????", example = "newTitle")
                                                    @RequestParam("newTitle") @NotNull String newTitle) {

        if (newTitle.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title's name is empty");
        }

        Optional<GroupChat> optionalGroupChat = groupChatService.getByIdWithChat(chatId);

        if (!optionalGroupChat.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("GroupChat with ID: %d does not exist.", chatId));
        }

        GroupChat groupChat = optionalGroupChat.get();
        groupChat.getChat().setTitle(newTitle);
        groupChatService.update(groupChat);

        return ResponseEntity.ok().body(groupChatConverter.groupChatToChatDto(groupChat));
    }
}