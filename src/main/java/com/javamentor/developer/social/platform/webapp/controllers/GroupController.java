package com.javamentor.developer.social.platform.webapp.controllers;

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
            @ApiResponse(code = 200, message = "Инфа обо всех группах получена", responseContainer = "List", response = GroupInfoDto.class)
    })
    @GetMapping(value = "/all", params = {"page", "size"})
    public ResponseEntity<List<GroupInfoDto>> getAllGroups(@ApiParam(value = "Текущая страница", example = "1") @RequestParam("page") int page,
                                                           @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("size") int size) {
        return new ResponseEntity<>(groupDtoService.getAllGroups(page, size), HttpStatus.OK);
    }

    @ApiOperation(value = "Получение информации об одной группе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Инфа о группе получена", response = GroupDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> showGroup(@ApiParam(value = "Идентификатор группы", example = "1") @PathVariable @NonNull Long id) {
        return new ResponseEntity<>(groupDtoService.getGroupById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Получение всех постов группы по id группы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посты для группы получены", responseContainer = "List", response = GroupWallDto.class)
    })
    @GetMapping(value = "/{id}/posts", params = {"page", "size"})
    public ResponseEntity<List<GroupWallDto>> showGroupWall(@ApiParam(value = "Идентификатор группы", example = "1") @PathVariable @NonNull Long id,
                                                            @ApiParam(value = "Текущая страница", example = "1") @RequestParam("page") int page,
                                                            @ApiParam(value = "Количество данных на страницу", example = "15") @RequestParam("size") int size) {
        return new ResponseEntity<>(groupDtoService.getPostsByGroupId(id, page, size), HttpStatus.OK);
    }

    @ApiOperation(value = "Получение группы по наименованию группы")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Группа найдена", response = GroupInfoDto.class),
            @ApiResponse(code = 400, message = "Группа не найдена")
    })
    @GetMapping(value = "/name", params = "name")
    public ResponseEntity<GroupInfoDto> findGroupByName(@ApiParam(value = "Наименование группы", example = "JAVA IS 1") @RequestParam("name") String name) {
        return new ResponseEntity<>(groupDtoService.getGroupByName(name), HttpStatus.OK);
    }
}
