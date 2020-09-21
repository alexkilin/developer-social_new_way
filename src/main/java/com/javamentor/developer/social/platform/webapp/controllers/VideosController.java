package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.VideoDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.VideoDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.VideosService;
import com.javamentor.developer.social.platform.webapp.converters.VideoConverter;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(value = "/api/videos", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "VideosApi", description = "Операции с видеозаписями(получение, сортировка, добавление)")
public class VideosController {

    private final VideosService videosService;
    private final VideoConverter videoConverter;
    private final VideoDtoService videoDtoService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public VideosController(VideosService videosService, VideoConverter videoConverter, VideoDtoService videoDtoService){
        this.videosService =  videosService;
        this.videoConverter = videoConverter;
        this.videoDtoService = videoDtoService;
    }

    @ApiOperation(value = "Получение всего видео")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все видео получено", responseContainer = "List",response = VideoDto.class)
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<VideoDto>> getAllVideos() {
        logger.info("Отправка всех видео записей");
        return ResponseEntity.ok().body(videosService.getAll().stream().map(videoConverter::toDTO).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получение некоторого количества видео")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Несколько видео получено",responseContainer = "List",response = VideoDto.class)})
    @GetMapping(value = "/getPart", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<List<VideoDto>> getPartVideos(@ApiParam(value = "Текущая страница", example = "1")@RequestParam("currentPage") int currentPage,
                                                        @ApiParam(value = "Количество данных на страницу", example = "15")@RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Видео начиная c объекта номер %s, в количестве %s отправлено", (currentPage - 1) * itemsOnPage + 1, itemsOnPage));
        return ResponseEntity.ok().body(videosService.getPart(currentPage, itemsOnPage).stream().map(videoConverter::toDTO).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получение видео по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "видео по названию получено",response = VideoDto.class)})
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<VideoDto> getVideoOfName(@ApiParam(value = "Название видео", example = "Test video 3")@PathVariable @NotNull String name) {
        logger.info(String.format("Отправка видео c названием %s", name));
        return ResponseEntity.ok().body(videoDtoService.getVideoOfName(name));
    }
}
