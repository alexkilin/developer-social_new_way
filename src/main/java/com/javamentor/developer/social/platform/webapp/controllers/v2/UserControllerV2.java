package com.javamentor.developer.social.platform.webapp.controllers.v2;

import com.javamentor.developer.social.platform.models.dto.FriendDto;
import com.javamentor.developer.social.platform.models.dto.StatusDto;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.models.util.OnUpdate;
import com.javamentor.developer.social.platform.service.abstracts.dto.FriendsDtoService;
import com.javamentor.developer.social.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserFriendsService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.UserConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/api/v2/users", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "UserApi-v2", description = "CRUD-Операции с пользователем")
public class UserControllerV2 {

    private final UserDtoService userDtoService;
    private final FriendsDtoService friendsDtoService;
    private final UserService userService;
    private final UserFriendsService userFriendsService;
    private final UserConverter userConverter;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserControllerV2(UserDtoService userDtoService, FriendsDtoService friendsDtoService, UserService userService, UserFriendsService userFriendsService, UserConverter userConverter) {
        this.userDtoService = userDtoService;
        this.friendsDtoService = friendsDtoService;
        this.userService = userService;
        this.userFriendsService = userFriendsService;
        this.userConverter = userConverter;
    }


    @ApiOperation(value = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь получен", response = UserDto.class),
            @ApiResponse(code = 404, message = "Пользователь не найден", response = String.class)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@ApiParam(value = "Идентификатор пользователя", example = "10") @PathVariable @Valid @NonNull Long id) {
        Optional<UserDto> optionalUserDto = userDtoService.getUserDtoById(id);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            logger.info(String.format("Пользователь с ID: %d получен!", id));
            return ResponseEntity.ok(userDto);
        } else {
            logger.info(String.format("Пользователь с указанным ID: %d не найден!", id));
            return ResponseEntity.status(404).body(String.format("User with ID: %d does not exist.", id));
        }
    }

    @ApiOperation(value = "Получение списка пользователей")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список пользователей получен", responseContainer = "List", response = UserDto.class)
    })
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getAll() {
        logger.info(String.format("Получен список пользователей"));
        return ResponseEntity.ok(userDtoService.getUserDtoList());
    }

    @ApiOperation(value = "Создание пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь создан", response = UserDto.class),
            @ApiResponse(code = 400, message = "Пользователь с данным email существует. Email должен быть уникальным", response = String.class)
    })
    @PostMapping("")
    @Validated(OnCreate.class)
    public ResponseEntity<?> create(@ApiParam(value = "Объект создаваемого пользователя") @RequestBody @Valid @NotNull UserDto userDto) {
        if (userService.existByEmail(userDto.getEmail())) {
            logger.info(String.format("Пользователь с email: %s уже существует", userDto.getEmail()));
            return ResponseEntity.status(400).body(String.format("User with email: %s already exist. Email should be unique", userDto.getEmail()));
        } else {
            User user = userConverter.toEntity(userDto);
            userService.create(user);
            logger.info(String.format("Пользователь с email: %s добавлен в базу данных", userDto.getEmail()));
            return ResponseEntity.ok(userConverter.toDto(user));
        }
    }

    @ApiOperation(value = "Обновление пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь обновлен", response = UserDto.class),
            @ApiResponse(code = 404, message = "Пользователь не обновлен из-за несоответствия id", response = String.class)
    })
    @PatchMapping("")
    @Validated(OnUpdate.class)
    public ResponseEntity<?> updateUser(@ApiParam(value = "Пользователь с обновленными данными") @Valid @RequestBody UserDto userDto) {
        User user = userConverter.toEntity(userDto);
        if (userService.existById(userDto.getUserId())) {
            userService.update(user);
            logger.info(String.format("Пользователь с ID: %d обновлён успешно", userDto.getUserId()));
            return ResponseEntity.ok(userConverter.toDto(user));
        } else {
            logger.info(String.format("Пользователь с ID: %d не существует", userDto.getUserId()));
            return ResponseEntity.status(404).body(String.format("User with ID: %d does not exist.", userDto.getUserId()));
        }
    }

    @ApiOperation(value = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удален", response = String.class),
            @ApiResponse(code = 404, message = "Пользователя с данным id нет в базе данных", response = String.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@ApiParam(value = "Идентификатор пользователя", example = "10") @PathVariable @NonNull Long id) {
        if (userService.existById(id)) {
            userService.deleteById(id);
            logger.info(String.format("Пользователь с ID: %d удалён успешно ", id));
            return ResponseEntity.ok(String.format("User with ID: %d deleted", id));
        } else {
            logger.info(String.format("Пользователь с ID: %d не удалён", id));
            return ResponseEntity.status(404).body(String.format("User with ID: %d does not exist.", id));
        }
    }

    @ApiOperation(value = "Получение списка друзей пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список друзей пользователя получен", responseContainer = "List", response = FriendDto.class),
            @ApiResponse(code = 400, message = "Пользователя с таким id не существует", response = String.class)
    })
    @GetMapping("/{id}/friends")
    public ResponseEntity<?> getUserFriends(@ApiParam(value = "Идентификатор пользователя", example = "10") @PathVariable @NonNull Long id) {
        if (userService.existById(id)) {
            List<FriendDto> userFriends = friendsDtoService.getUserFriendsDtoById(id);
            logger.info("Получен список друзей пользователя");
            return ResponseEntity.ok(userFriends);
        } else {
            logger.info("Пользователя с таким id не существует, список друзей пользователя не получен");
            return ResponseEntity.status(400).body(String.format("User with ID: %d does not exist.", id));
        }
    }

    @ApiOperation(value = "Изменение статуса пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Статус пользователя изменён", response = UserDto.class),
            @ApiResponse(code = 404, message = "Пользователь не найден", response = String.class)
    })
    @PatchMapping(value = "/status")
    public ResponseEntity<?> updateUserStatus(@ApiParam(value = "Статус пользователя") @Valid @RequestBody StatusDto statusDto) {
        Optional<UserDto> optionalUserDto = userDtoService.getUserDtoById(statusDto.getUserId());
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            userDto.setStatus(statusDto.getStatus());
            userService.update(userConverter.toEntity(userDto));
            logger.info("Статус изменён");
            return ResponseEntity.ok(userDto);
        } else {
            logger.info(String.format("Пользователь с указанным ID: %d не найден!", statusDto.getUserId()));
            return ResponseEntity.status(404).body(String.format("User with ID: %d does not exist.", statusDto.getUserId()));
        }
    }
}


