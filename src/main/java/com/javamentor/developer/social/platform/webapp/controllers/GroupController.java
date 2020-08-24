package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @GetMapping(value = "/all", params = {"page", "size"})
    public ResponseEntity<List<GroupInfoDto>> getAllGroups(@RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(groupDtoService.getAllGroups(page, size), HttpStatus.OK);
    }

    @ApiOperation(value = "Получение информации об одной группе")
    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> showGroup(@PathVariable Long id) {
        return new ResponseEntity<>(groupDtoService.getGroupById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Получение всех постов группы по id группы")
    @GetMapping(value = "/{id}/posts", params = {"page", "size"})
    public ResponseEntity<List<GroupWallDto>> showGroupWall(@PathVariable Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return new ResponseEntity<>(groupDtoService.getPostsByGroupId(id, page, size), HttpStatus.OK);
    }

    @ApiOperation(value = "Получение группы по наименованию группы")
    @GetMapping(value = "/name", params = "name")
    public ResponseEntity<GroupInfoDto> findGroupByName(@RequestParam("name") String name) {
        return new ResponseEntity<>(groupDtoService.getGroupByName(name), HttpStatus.OK);
    }
}
