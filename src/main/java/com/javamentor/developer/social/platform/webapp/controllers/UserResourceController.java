package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.service.impl.UserControllerDtoService;
import com.javamentor.developer.social.platform.webapp.converters.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
@Api(value = "UserApi", description = "CRUD-Операции с пользователем")
public class UserResourceController {

    private final UserControllerDtoService userService;
    private final UserConverter converter;

    @Autowired
    public UserResourceController(UserControllerDtoService userService, UserConverter converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @ApiOperation(value = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь получен")
    })
    @GetMapping(value = "/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(converter.convertToDto(userService.getById(id)));
    }

    @ApiOperation(value = "Получение списка пользователей")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен")
    })
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ApiOperation(value = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь создан")
    })
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        userService.create(user);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Обновление пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь обновлен")
    })
    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        userService.update(user);
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
    public ResponseEntity<List<UserDto>> getUserFriends(@PathVariable Long id) {
        List<UserDto> userFriends = userService.getUserFriendsById(id);
        return ResponseEntity.ok(userFriends);
    }
}
