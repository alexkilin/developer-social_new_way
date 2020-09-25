package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupHasUserService;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/groupsHasUsers")
@Api(value = "GroupHasUsersApi", description = "Операции над группами")
public class GroupHasUserController {
    public final GroupHasUserService groupHasUserService;
    public final UserService userService;
    public final GroupService groupService;

    @Autowired
    public GroupHasUserController(GroupHasUserService groupHasUserService, UserService userService, GroupService groupService) {
        this.groupHasUserService = groupHasUserService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @ApiOperation(value = "Вступление в группу пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пользователь вступил в группу"),
            @ApiResponse(code = 400, message = "Пользователь уже есть в данной группе"),
            @ApiResponse(code = 404, message = "Пользователь или группа не найдены")
    })
    @PostMapping(value = "/add", params = {"groupId", "userId"})
    public ResponseEntity<String> UserJoinGroup(@ApiParam(value = "Идентификатор группы", example = "1") @RequestParam("groupId") @NonNull Long groupId,
                                              @ApiParam(value = "Идентификатор пользователя", example = "1") @RequestParam("userId") @NonNull Long userId) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/add").
                buildAndExpand().toUri();
        if (groupHasUserService.verificationUserInGroup(groupId,userId)) {
            String msg = String.format("Пользователь с id: %d уже есть в группе с id: %s", userId, groupId);
            return ResponseEntity.badRequest().body(msg);
        }
        if (userService.existById(userId) & groupService.existById(groupId)) {
            User user = userService.getById(userId);
            Group group = groupService.getById(groupId);
            groupHasUserService.setUserIntoGroup(user, group);
            String msg = String.format("Пользователь с id: %d добавлен в группу с id: %s", userId, groupId);
            return ResponseEntity.created(location).body(msg);
        } else {
            //String msg = String.format("Пользователь с id: %d и/или группа с id: %s не найдены", userId, groupId);
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Удаление пользователя из группы")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Пользователь удален из группы", response = String.class),
            @ApiResponse(code = 404, message = "Пользователь или группа не найдены")
    })
    @DeleteMapping(value = "/delete", params = {"groupId", "userId"})
    public ResponseEntity<?> deleteUserById(@ApiParam(value = "Идентификатор группы", example = "10") @RequestParam("groupId") @NonNull Long groupId,
                                                 @ApiParam(value = "Идентификатор юзера", example = "1") @RequestParam("userId") @NonNull Long userId){
        if (groupHasUserService.verificationUserInGroup(groupId, userId)) {
            groupHasUserService.deleteUserById(groupId, userId);
            String msg = String.format("Пользователь с id: %d удален из группы с id: %s", userId, groupId);
            return ResponseEntity.ok().body(msg);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
