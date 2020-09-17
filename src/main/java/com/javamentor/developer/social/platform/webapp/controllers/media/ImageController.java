package com.javamentor.developer.social.platform.webapp.controllers.media;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.dto.ImageDTOService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.ImageConverter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/image")
@Api(value = "ImageApi")
public class ImageController {

    @ApiOperation(value = "Получить все фотоальбомы пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фотоальбомы получены", response = AudioDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Фотоальбомы не найдены", response = String.class)})
    @GetMapping(value = "/user/{userId}/albums")
    public ResponseEntity<?> getAllImageAlbumsOfUser(
            @ApiParam(value = "Id пользователя", example = "60") @PathVariable @NotNull Long userId,
            @ApiParam(value = "Страница", example = "0") @RequestParam("currentPage") @NotNull int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "5") @RequestParam("itemsOnPage") @NotNull int itemsOnPage) {
        return null;
    }

    @ApiOperation(value = "Получить фотоальбом по Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фотоальбомы получены", response = AudioDto.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Фотоальбомы не найдены", response = String.class)})
    @GetMapping(value = "/albums")
    public ResponseEntity<?> getAllImagesOfAlbum(
            @ApiParam(value = "Id пользователя", example = "60") @PathVariable @NotNull Long userId,
            @ApiParam(value = "Страница", example = "0") @RequestParam("currentPage") @NotNull int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "5") @RequestParam("itemsOnPage") @NotNull int itemsOnPage) {
        return null;
    }

}