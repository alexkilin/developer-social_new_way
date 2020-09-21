package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.VideoDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.VideoDtoService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumVideoService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.VideosService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumConverter;
import com.javamentor.developer.social.platform.webapp.converters.VideoConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private final UserService userService;
    private final AlbumService albumService;
    private final AlbumVideoService albumVideoService;
    private final AlbumConverter albumConverter;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public VideosController(VideosService videosService, VideoConverter videoConverter, VideoDtoService videoDtoService,
                            UserService userService, AlbumService albumService, AlbumVideoService albumVideoService,
                            AlbumConverter albumConverter){
        this.videosService =  videosService;
        this.videoConverter = videoConverter;
        this.videoDtoService = videoDtoService;
        this.userService = userService;
        this.albumService = albumService;
        this.albumVideoService = albumVideoService;
        this.albumConverter = albumConverter;
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

    @ApiOperation(value = "Получение всего видео из коллекции пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все видео из коллекции пользователя",response = VideoDto.class, responseContainer = "List")})
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<VideoDto>> getVideoOfUser(
            @ApiParam(value = "Id юзера", example = "4")@PathVariable("userId") @NonNull Long userId) {
        logger.info(String.format("Отправка всего видео пользователя %s", userId));
        return ResponseEntity.ok().body(videoDtoService.getVideoOfUser(userId));
    }

    @ApiOperation(value = "Получение видео из коллекции пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Видео из коллекции пользователя по частям",responseContainer = "List",response = VideoDto.class)})
    @GetMapping(value = "/PartVideoOfUser/{userId}", params = {"currentPage", "itemsOnPage"})
    public ResponseEntity<List<VideoDto>> getPartVideoOfUser(
            @ApiParam(value = "Текущая страница", example = "0")@RequestParam("currentPage") int currentPage,
            @ApiParam(value = "Количество данных на страницу", example = "1")@RequestParam("itemsOnPage") int itemsOnPage,
            @ApiParam(value = "Id юзера", example = "4")@PathVariable("userId") @NonNull Long userId) {
        logger.info(String.format("Видео пользователя %s начиная c объекта номер %s, в количестве %s отправлено ", userId, (currentPage - 1) * itemsOnPage + 1, itemsOnPage));
        return ResponseEntity.ok().body(videoDtoService.getPartVideoOfUser(userId, currentPage, itemsOnPage));
    }

    @ApiOperation(value = "Добавление видео")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Видео успешно добавлено", response = VideoDto.class)})
    @PostMapping(value = "/add")
    public ResponseEntity<?> addVideo(@ApiParam(value = "Объект добавляемого видео")@RequestBody @Valid @NonNull VideoDto videoDto) {
        User user = userService.getById(60L);
        Videos audios = videoConverter.toVideo(videoDto, MediaType.VIDEO, user);
        videosService.create(audios);
        logger.info(String.format("Добавление видео с id %s в бд", videoDto.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(videoDto);
    }

    @ApiOperation(value = "Создание видео альбома пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Видео альбом успешно создан", response = AlbumDto.class),
            @ApiResponse(code = 400, message = "Неверные параметры", response = String.class)})
    @Validated(OnCreate.class)
    @PostMapping(value = "/createVideoAlbum")
    public ResponseEntity<?> createVideoAlbum(@ApiParam(value = "объект создаваемого альбома")@RequestBody @NotNull @Valid AlbumDto albumDto) {
        if(albumService.existsByNameAndMediaType(albumDto.getName(), MediaType.AUDIO)) {
            return ResponseEntity.badRequest()
                    .body(String.format("Audio album with name '%s' already exists", albumDto.getName()));
        }
        AlbumVideo albumVideo = albumVideoService.createAlbumVideosWithOwner(
                albumConverter.toAlbumVideo(albumDto, userService.getById(60L)));
        logger.info(String.format("Альбом с именем  %s создан", albumDto.getName()));
        return ResponseEntity.ok().body(albumConverter.toAlbumDto(albumVideo));
    }
}
