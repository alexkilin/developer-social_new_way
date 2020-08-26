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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> UserJoinGroup(@ApiParam(value = "Идентификатор группы", example = "1") @RequestParam("groupId") @NonNull Long groupId,
                                              @ApiParam(value = "Идентификатор пользователя", example = "1") @RequestParam("userId") @NonNull Long userId) {
        if (groupHasUserService.verificationUserInGroup(groupId,userId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.existById(userId) & groupService.existById(groupId)) {
            User user = userService.getById(userId);
            Group group = groupService.getById(groupId);
            groupHasUserService.setUserIntoGroup(user, group);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
