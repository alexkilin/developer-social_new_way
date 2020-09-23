package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/groups")
@Api(value = "GroupsApi", description = "Операции по получению групп")
public class GroupController {
    private final GroupDtoService groupDtoService;

    @Autowired
    public GroupController(GroupDtoService groupDtoService) {
        this.groupDtoService = groupDtoService;
    }

    @ApiOperation(value = "Получение информации обо всех группах")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Инфа обо всех группах получена", responseContainer = "List", response = GroupInfoDto.class),
            @ApiResponse(code = 400, message = "Неверные параметры", response = String.class)
    })
    @GetMapping(value = "/all", params = {"page", "size"})
    public ResponseEntity<List<GroupInfoDto>> getAllGroups(@ApiParam(value = "Текущая страница", example = "1") @RequestParam("page") int page,
                                                           @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("size") int size) {
        return ResponseEntity.ok().body(groupDtoService.getAllGroups(page, size));
    }

    @ApiOperation(value = "Получение информации об одной группе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация о группе получена", response = GroupDto.class),
            @ApiResponse(code = 404, message = "Группа с данным id не найдена", response = String.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> showGroup(@ApiParam(value = "Идентификатор группы", example = "1") @PathVariable @NonNull Long id) {
        Optional<GroupDto> groupDtoOptional = groupDtoService.getGroupById(id);
        if(!groupDtoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Группа с id %s не найдена", id));
        }
        return ResponseEntity.ok().body(groupDtoOptional.get());
    }

    @ApiOperation(value = "Получение всех постов группы по id группы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты группы получены", responseContainer = "List", response = GroupWallDto.class)
    })
    @GetMapping(value = "/{id}/posts", params = {"page", "size"})
    public ResponseEntity<List<GroupWallDto>> showGroupWall(@ApiParam(value = "Идентификатор группы", example = "1") @PathVariable @NonNull Long id,
                                                            @ApiParam(value = "Текущая страница", example = "1") @RequestParam("page") int page,
                                                            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("size") int size) {
        return ResponseEntity.ok().body(groupDtoService.getPostsByGroupId(id, page, size));
    }

    @ApiOperation(value = "Получение группы по наименованию группы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Группа найдена", response = GroupInfoDto.class),
            @ApiResponse(code = 404, message = "Группа не найдена", response = String.class)
    })
    @GetMapping(value = "/name", params = "name")
    public ResponseEntity<?> findGroupByName(@ApiParam(value = "Наименование группы", example = "JAVA IS 1") @RequestParam("name") String name) {
        Optional<GroupInfoDto> groupInfoDto = groupDtoService.getGroupByName(name);
        if(!groupInfoDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Группа с именем %s не найдена", name));
        }
        return ResponseEntity.ok().body(groupInfoDto.get());
    }

    @ApiOperation(value = "Получение всех юзеров, входящих в группу")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Юзеры группы получены", responseContainer = "List",response = UserDto.class),
            @ApiResponse(code = 404, message = "Группа не найдена", response = String.class)
    })
    @GetMapping(value = "/{id}/users")
    public ResponseEntity<?> getUsersFromTheGroup(@ApiParam(value = "Идентификатор группы", example = "1") @PathVariable @NonNull Long id){
        Optional<GroupDto> groupDtoOptional = groupDtoService.getGroupById(id);
        if(!groupDtoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Группа с id %s не найдена", id));
        }
        return ResponseEntity.ok(groupDtoService.getUsersFromTheGroup(id));
    }
}
