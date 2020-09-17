package com.javamentor.developer.social.platform.webapp.controllers.media;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping(value = "/api/image")
@Api(value = "ImageApi")
public class ImageController {

    @ApiOperation(value = "Создать изображение")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Изображение создано", response = AudioDto.class)})
    @PostMapping(value = "/image")
    public ResponseEntity<?> createImage() {
        return null;
    }

    @ApiOperation(value = "Получить все фотоальбомы пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фотоальбомы получены", response = AudioDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Фотоальбомы не найдены", response = String.class)})
    @GetMapping(value = "/albums")
    public ResponseEntity<?> getAllImageAlbumsOfUser(
            @ApiParam(value = "Id пользователя", example = "60") @RequestParam("userId") @NotNull Long userId,
            @ApiParam(value = "Страница", example = "0") @RequestParam("currentPage") @NotNull int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "5") @RequestParam("itemsOnPage") @NotNull int itemsOnPage) {
        return null;
    }

    @ApiOperation(value = "Получить фотоальбом по Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фотоальбом получен", response = AudioDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Фотоальбом не найден", response = String.class)})
    @GetMapping(value = "/albums/{userId}")
    public ResponseEntity<?> getImageAlbumById(
            @ApiParam(value = "Id пользователя", example = "60") @PathVariable @NotNull Long userId) {
        return null;
    }

}