package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.VideoDto;
import com.javamentor.developer.social.platform.service.abstracts.model.media.VideosService;
import com.javamentor.developer.social.platform.webapp.converters.VideoConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(value = "/api/videos", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "VideosApi", description = "Операции с видеозаписями(получение, сортировка, добавление)")
public class VideoController {

    private final VideosService videosService;
    private final VideoConverter videoConverter;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public VideoController(VideosService videosService, VideoConverter videoConverter){
        this.videosService =  videosService;
        this.videoConverter = videoConverter;
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
}
