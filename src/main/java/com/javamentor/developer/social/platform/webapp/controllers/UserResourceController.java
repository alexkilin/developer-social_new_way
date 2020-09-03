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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
@Api(value = "UserApi", description = "CRUD-Операции с пользователем")
public class    UserResourceController {

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
            @ApiResponse(code = 200, message = "Пользователь получен")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UserDto> optionalUserDto = userDtoService.getUserDtoById(id);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            return ResponseEntity.ok(userDto);
        }
        logger.info(String.format("Пользователь с указанным ID: %d не найден!", id));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with ID: %d does not exist", id));
    }

    @ApiOperation(value = "Получение списка пользователей")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен")
    })
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        logger.info(String.format("Получен список пользователей"));
        return ResponseEntity.ok(userDtoService.getUserDtoList());
    }

    @ApiOperation(value = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь создан")
    })
    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        userService.create(userConverter.toEntity(userDto));
        logger.info(String.format("Пользователь с email: %s добавлен в базу данных", userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }

    @ApiOperation(value = "Обновление пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь обновлен")
    })
    @PutMapping("/update")
    @Validated(OnUpdate.class)
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto) {
        User user = userConverter.toEntity(userDto);
        if (userService.existById(userDto.getUserId())) {
            userService.update(user);
            logger.info(String.format("Пользователь с ID: %d обновлён успешно", userDto.getUserId()));
            return ResponseEntity.ok(userConverter.toDto(user));
        }
        logger.info(String.format("Пользователь с ID: %d не существует", userDto.getUserId()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("User with ID: %d does not exist", userDto.getUserId()));
    }

    @ApiOperation(value = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удален")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        logger.info(String.format("Пользователь с ID: %d удалён успешно ", id));
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Получение списка друзей пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список друзей пользователя получен")
    })
    @GetMapping("/getUserFriends")
    public ResponseEntity<List<FriendDto>> getUserFriends(@PathVariable Long id) {
        List<Friend> userFriends = userFriendsService.getUserFriendsById(id);
        logger.info(String.format("Получен список друзей пользователя"));
        return ResponseEntity.ok(friendsConverter.toDto(userFriends));
    }
}
