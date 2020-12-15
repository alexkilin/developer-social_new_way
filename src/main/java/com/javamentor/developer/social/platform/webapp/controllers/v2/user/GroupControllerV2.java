package com.javamentor.developer.social.platform.webapp.controllers.v2.user;

import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import com.javamentor.developer.social.platform.models.dto.group.*;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupHasUserService;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.GroupConverter;
import com.javamentor.developer.social.platform.webapp.converters.GroupHasUserConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v2/groups", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "GroupsApi-v2", description = "Операции над группами")
public class GroupControllerV2 {
    private final GroupDtoService groupDtoService;
    private final GroupHasUserService groupHasUserService;
    private final UserService userService;
    private final GroupService groupService;
    private final GroupConverter groupConverter;
    private final GroupHasUserConverter groupHasUserConverter;

    @Autowired
    public GroupControllerV2(GroupDtoService groupDtoService, GroupHasUserService groupHasUserService, UserService userService, GroupService groupService, GroupConverter groupConverter, GroupHasUserConverter groupHasUserConverter) {
        this.groupDtoService = groupDtoService;
        this.groupHasUserService = groupHasUserService;
        this.userService = userService;
        this.groupService = groupService;
        this.groupConverter = groupConverter;
        this.groupHasUserConverter = groupHasUserConverter;
    }

    @ApiOperation(value = "Получение информации обо всех группах")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Инфа обо всех группах получена", responseContainer = "List", response = GroupInfoDto.class),
            @ApiResponse(code = 400, message = "Неверные параметры", response = String.class)
    })
    @GetMapping(params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<Object, Object>> getAllGroups(
            @ApiParam(value = "Текущая страница", example = "0") @RequestParam("currentPage") int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("itemsOnPage") int itemsOnPage) {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(groupDtoService.getAllGroups(parameters));
    }

    @ApiOperation(value = "Получение информации об одной группе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация о группе получена", response = GroupDto.class),
            @ApiResponse(code = 400, message = "Группа с данным id не найдена", response = String.class)
    })
    @GetMapping("/{groupId}")
    public ResponseEntity<?> showGroup(@ApiParam(value = "Идентификатор группы", example = "1")
                                       @PathVariable @NonNull Long groupId) {
        Optional<GroupDto> groupDtoOptional = groupDtoService.getGroupById(groupId);
        if (!groupDtoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Group id %s not found", groupId));
        }
        return ResponseEntity.ok().body(groupDtoOptional.get());
    }

    @ApiOperation(value = "Получение всех постов группы по id группы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты группы получены", responseContainer = "List", response = GroupWallDto.class)
    })
    @GetMapping(value = "/{groupId}/posts", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<PageDto<GroupWallDto, Object>> showGroupWall(@ApiParam(value = "Идентификатор группы", example = "1")
                                                                       @PathVariable @NonNull Long groupId,
                                                                       @ApiParam(value = "Текущая страница", example = "0")
                                                                       @RequestParam("currentPage") int currentPage,
                                                                       @ApiParam(value = "Количество данных на страницу", example = "15")
                                                                       @RequestParam("itemsOnPage") int itemsOnPage) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("groupId", groupId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);
        return ResponseEntity.ok().body(groupDtoService.getPostsByGroupId(parameters));
    }

    @ApiOperation(value = "Получение группы по наименованию группы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Группа найдена", response = GroupInfoDto.class),
            @ApiResponse(code = 400, message = "Группа не найдена", response = String.class)
    })
    @GetMapping(value = "/name", params = "name")
    public ResponseEntity<?> findGroupByName(@ApiParam(value = "Наименование группы", example = "JAVA IS 1")
                                             @RequestParam("name") String name) {
        Optional<GroupDto> groupInfoDto = groupDtoService.getGroupByName(name);
        if (!groupInfoDto.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("Group name %s not found", name));
        }
        return ResponseEntity.ok().body(groupInfoDto.get());
    }

    @ApiOperation(value = "Получение всех юзеров, входящих в группу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Юзеры группы получены", responseContainer = "List", response = UserDto.class),
            @ApiResponse(code = 400, message = "Группа не найдена", response = String.class)
    })
    @GetMapping(value = "/{groupId}/users", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<?> getUsersFromTheGroup(@ApiParam(value = "Идентификатор группы", example = "1")
                                                  @PathVariable @NonNull Long groupId,
                                                  @ApiParam(value = "Текущая страница", example = "0")
                                                  @RequestParam("currentPage") int currentPage,
                                                  @ApiParam(value = "Количество данных на страницу", example = "15")
                                                  @RequestParam("itemsOnPage") int itemsOnPage) {
        Optional<GroupDto> groupDtoOptional = groupDtoService.getGroupById(groupId);
        if (!groupDtoOptional.isPresent()) {
            return ResponseEntity.badRequest().body(String.format("Group id %s not found", groupId));
        }
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("groupId", groupId);
        parameters.put("currentPage", currentPage);
        parameters.put("itemsOnPage", itemsOnPage);

        return ResponseEntity.ok(groupDtoService.getUsersFromTheGroup(parameters));
    }

    @ApiOperation(value = "Вступление в группу пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь вступил в группу"),
            @ApiResponse(code = 200, message = "Пользователь уже есть в данной группе"),
            @ApiResponse(code = 400, message = "Пользователь или группа не найдены")
    })
    @PutMapping(value = "/{groupId}/users", params = "userId")
    public ResponseEntity<String> userJoinGroup(@ApiParam(value = "Идентификатор группы", example = "1")
                                                @PathVariable("groupId") @NonNull Long groupId,
                                                @ApiParam(value = "Идентификатор пользователя", example = "1")
                                                @RequestParam("userId") @NonNull Long userId) {
        if (groupHasUserService.verificationUserInGroup(groupId,userId)) {
            return ResponseEntity.ok()
                    .body(String.format("User with id: %d already a member of the group with id: %s", userId, groupId));
        }
        Optional <User> user = userService.getById(userId);
        Optional <Group> group = groupService.getById(groupId);
        if (user.isPresent() && group.isPresent()) {
            groupHasUserService.setUserIntoGroup(user.get(), group.get());
            return ResponseEntity.ok().body(String.format("User with id: %d added to the group with id: %s", userId, groupId));
        }
        return ResponseEntity.badRequest()
                .body(String.format("User with id: %d or/and group with id: %s not found", userId, groupId));
    }

    @ApiOperation(value = "Есть ли пользователь в группе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Поиск пользователя в группе", response = GroupHasUserInfoDto.class),
            @ApiResponse(code = 400, message = "Пользователь и/или группа не найдены", response = String.class)
    })
    @GetMapping(value = "/{groupId}/users", params = "userId")
    public ResponseEntity<?> groupHasUser(@ApiParam(value = "Идентификатор группы", example = "1")
                                          @PathVariable("groupId") @NonNull Long groupId,
                                          @ApiParam(value = "Идентификатор пользователя", example = "1")
                                          @RequestParam("userId") @NonNull Long userId) {
        if (!(userService.existById(userId) && groupService.existById(groupId))) {
            return ResponseEntity.badRequest()
                    .body(String.format("User with id: %d or/and group with id: %s not found", userId, groupId));
        }
        return ResponseEntity.ok(groupHasUserConverter.toGroupHasUserInfoDto(groupId, groupHasUserService.verificationUserInGroup(groupId,userId)));
    }

    @ApiOperation(value = "Удаление пользователя из группы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Пользователь удален из группы", response = String.class),
            @ApiResponse(code = 400, message = "Пользователь или группа не найдены")
    })
    @DeleteMapping(value = "/{groupId}/users", params = "userId")
    public ResponseEntity<?> deleteUserById(@ApiParam(value = "Идентификатор группы", example = "10")
                                            @PathVariable("groupId") @NonNull Long groupId,
                                            @ApiParam(value = "Идентификатор юзера", example = "1")
                                            @RequestParam("userId") @NonNull Long userId){
        if (groupHasUserService.verificationUserInGroup(groupId, userId)) {
            groupHasUserService.deleteUserById(groupId, userId);
            return ResponseEntity.ok().body(String.format("User with id: %d is no longer a member of the group with id: %s", userId, groupId));
        }
        return ResponseEntity.badRequest()
                .body(String.format("User with id %s is not a member of a group", userId));
    }

    @ApiOperation(value = "Изменение группы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Группа изменена", response = GroupDto.class),
            @ApiResponse(code = 400, message = "Группа не найдена", response = String.class)
    })
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateGroup(@ApiParam(value = "Группа с обновленными данными")
                                         @Valid @RequestBody GroupUpdateInfoDto groupUpdateInfoDto) {
        if (!groupService.existById(groupUpdateInfoDto.getId())) {
            return ResponseEntity.badRequest()
                    .body(String.format("Group with id %s not found", groupUpdateInfoDto.getId()));
        }
        Group group = groupConverter.groupUpdateInfoDtoToGroup(groupUpdateInfoDto);
        groupService.updateInfo(group);
        return ResponseEntity.ok(groupConverter.groupToGroupUpdateInfoDto(group));
    }

}
