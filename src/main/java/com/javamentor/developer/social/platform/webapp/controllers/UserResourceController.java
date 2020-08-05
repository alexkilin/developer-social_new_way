package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.FriendDto;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.UserFriendsService;
import com.javamentor.developer.social.platform.service.abstracts.model.UserService;
import com.javamentor.developer.social.platform.webapp.converters.UserFriendsMapper;
import com.javamentor.developer.social.platform.webapp.converters.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
@Api(value = "UserApi", description = "CRUD-Операции с пользователем")
public class UserResourceController {

    private final UserDtoService userDtoService;
    private final UserService userService;
    private final UserFriendsService userFriendsService;
    private final UserMapper userMapper;
    private final UserFriendsMapper friendsMapper;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserResourceController(UserDtoService userDtoService, UserService userService, UserFriendsService userFriendsService,
                                  UserMapper userMapper, UserFriendsMapper friendsMapper) {
        this.userDtoService = userDtoService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.friendsMapper = friendsMapper;
        this.userFriendsService = userFriendsService;
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
        return ResponseEntity.ok(userDtoService.getUserDtoList());
    }

    @ApiOperation(value = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь создан")
    })
    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        userService.create(userMapper.INSTANCE.toEntity(user));
        logger.info(String.format("Пользователь с email: %s добавлен в базу данных", user.getEmail()));
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Обновление пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь обновлен")
    })
    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        userService.update(userMapper.INSTANCE.toEntity(user));
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удален")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Success");
    }

    @ApiOperation(value = "Получение списка друзей пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список друзей пользователя получен")
    })
    @GetMapping("/getUserFriends")
    public ResponseEntity<List<FriendDto>> getUserFriends(@PathVariable Long id) {
        List<Friend> userFriends = userFriendsService.getUserFriendsById(id);
        return ResponseEntity.ok(friendsMapper.INSTANCE.toDto(userFriends));
    }
}
