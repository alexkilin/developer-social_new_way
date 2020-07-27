package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.UserFriendsService;
import com.javamentor.developer.social.platform.service.abstracts.model.UserService;
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
    private final UserConverter converter;
    private final UserFriendsConverter friendsConverter;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserResourceController(UserDtoService userDtoService, UserService userService, UserConverter converter, UserFriendsService userFriendsService, UserFriendsConverter friendsConverter) {
        this.userDtoService = userDtoService;
        this.userService = userService;
        this.converter = converter;
        this.userFriendsService = userFriendsService;
        this.friendsConverter = friendsConverter;
    }

    @ApiOperation(value = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь получен")
    })
    @GetMapping(value = "/getUserById/{id}")
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
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userDtoService.getUserDtoList());
    }

    @ApiOperation(value = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь создан")
    })
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        userService.persist(converter.convertToEntity(user));
        logger.info(String.format("Пользователь с email: %s добавлен в базу данных", user.getEmail()));
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Обновление пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь обновлен")
    })
    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        userService.update(converter.convertToEntity(user));
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удален")
    })
    @DeleteMapping("/deleteUserById/{id}")
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
        return ResponseEntity.ok(friendsConverter.convertToDto(userFriends));
    }
}
