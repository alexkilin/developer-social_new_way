package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.FriendDto;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import com.javamentor.developer.social.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserFriendsService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.UserConverter;
import com.javamentor.developer.social.platform.webapp.converters.UserFriendsConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
@Api(value = "UserApi", description = "CRUD-Операции с пользователем")
public class UserResourceController {

    private final UserDtoService userDtoService;
    private final UserService userService;
    private final UserFriendsService userFriendsService;
    private final UserConverter userConverter;
    private final UserFriendsConverter friendsConverter;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserResourceController(UserDtoService userDtoService, UserService userService, UserFriendsService userFriendsService,
                                  UserConverter userConverter, UserFriendsConverter friendsConverter) {
        this.userDtoService = userDtoService;
        this.userService = userService;
        this.userFriendsService = userFriendsService;
        this.userConverter = userConverter;
        this.friendsConverter = friendsConverter;
    }

    @ApiOperation(value = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь получен"),
            @ApiResponse(code = 400, message = "Пользователь не найден", response = Null.class)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@ApiParam(value = "Идентификатор пользователя", example = "10") @PathVariable @NonNull Long id) {
        Optional<UserDto> optionalUserDto = userDtoService.getUserDtoById(id);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            logger.info(String.format("Пользователь с ID: %d получен!", id));
            return ResponseEntity.ok(userDto);
        }
        logger.info(String.format("Пользователь с указанным ID: %d не найден!", id));
        return ResponseEntity.status(400).body(null);
    }

    @ApiOperation(value = "Получение списка пользователей")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен", responseContainer = "List", response = UserDto.class)
    })
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        logger.info(String.format("Получен список пользователей"));
        return ResponseEntity.ok(userDtoService.getUserDtoList());
    }

    @ApiOperation(value = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь создан", response = UserDto.class)
    })
    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@ApiParam(value = "Объект создаваемого пользователя") @RequestBody @Valid @NonNull UserDto userDto) {
        userService.create(userConverter.toEntity(userDto));
        logger.info(String.format("Пользователь с email: %s добавлен в базу данных", userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation(value = "Обновление пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь обновлен", response = UserDto.class),
            @ApiResponse(code = 400, message = "Пользователь не обновлен из-за несоответствия id", response = Null.class)
    })
    @PutMapping("/update")
    @Validated(OnUpdate.class)
    public ResponseEntity<UserDto> updateUser(@ApiParam(value = "Пользователь с обновленными данными") @Valid @RequestBody UserDto userDto) {
        User user = userConverter.toEntity(userDto);
        if (userService.existById(userDto.getUserId())) {
            userService.update(user);
            logger.info(String.format("Пользователь с ID: %d обновлён успешно", userDto.getUserId()));
            return ResponseEntity.ok(userConverter.toDto(user));
        }
        logger.info(String.format("Пользователь с ID: %d не существует", userDto.getUserId()));
        return ResponseEntity.status(400).body(null);
    }

    @ApiOperation(value = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удален", response = String.class),
            @ApiResponse(code = 400, message = "Пользователь не удален из-за несоответствия id", response = String.class)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@ApiParam(value = "Идентификатор пользователя", example = "10") @PathVariable @NonNull Long id) {
        if (userService.existById(id)) {
            userService.deleteById(id);
            logger.info(String.format("Пользователь с ID: %d удалён успешно ", id));
            return ResponseEntity.ok("Success");
        } else {
            logger.info(String.format("Пользователь с ID: %d не удалён", id));
            return ResponseEntity.status(400).body("User with such ID not found!");
        }
    }

    @ApiOperation(value = "Получение списка друзей пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список друзей пользователя получен", responseContainer = "List", response = FriendDto.class),
            @ApiResponse(code = 400, message = "Пользователя с таким id не существует", response = Null.class)
    })
    @GetMapping("/getUserFriends")
    public ResponseEntity<List<FriendDto>> getUserFriends(@ApiParam(value = "Идентификатор пользователя", example = "10") @PathVariable @NonNull Long id) {
        if (userService.existById(id)) {
            List<Friend> userFriends = userFriendsService.getUserFriendsById(id);
            logger.info(String.format("Получен список друзей пользователя"));
            return ResponseEntity.ok(friendsConverter.toDto(userFriends));
        } else {
            logger.info(String.format("Пользователя с таким id не существует, список друзей пользователя не получен"));
            return ResponseEntity.status(400).body(null);
        }
    }
}

