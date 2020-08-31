package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.AlbumDto;
import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.models.util.OnCreate;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumServiceDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioServiceDto;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AudiosService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AlbumConverter;
import com.javamentor.developer.social.platform.webapp.converters.AudioConverter;
import io.swagger.annotations.*;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(value = "/api/audios", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "AudiosApi", description = "Операции с аудиозаписями(получение, сортировка, добавление)")
public class AudiosController {

    private final AudioConverter audioConverter;
    private final AudioServiceDto audioServiceDto;
    private final AudiosService audiosService;
    private final UserService userService;
    private final AlbumServiceDto albumServiceDto;
    private final AlbumAudioService albumAudioService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AudiosController(AudioConverter audioConverter, AudioServiceDto audioServiceDto, AudiosService audiosService, UserService userService, AlbumServiceDto albumServiceDto, AlbumAudioService albumAudioService) {
        this.audioConverter = audioConverter;
        this.audioServiceDto = audioServiceDto;
        this.audiosService = audiosService;
        this.userService = userService;
        this.albumServiceDto = albumServiceDto;
        this.albumAudioService = albumAudioService;
    }

    @ApiOperation(value = "Получение всего аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио получено", responseContainer = "List",response = AudioDto.class)
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<AudioDto>> getAllAudios() {
        logger.info("Отправка всех аудио записей");
        return ResponseEntity.ok().body(audiosService.getAll().stream().map(audioConverter::toDTO).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получение некоторого количества аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Несколько аудио получено",responseContainer = "List",response = AudioDto.class)})
    @GetMapping(value = "/getPart")
    public ResponseEntity<List<AudioDto>> getPartAudios(@ApiParam(value = "Текущая страница", example = "1")@RequestParam("currentPage") int currentPage,
                                                        @ApiParam(value = "Количество данных на страницу", example = "15")@RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Аудио начиная c объекта номер %s, в количестве %s отправлено", (currentPage - 1) * itemsOnPage + 1, itemsOnPage));
        return ResponseEntity.ok().body(audiosService.getPart(currentPage, itemsOnPage).stream().map(audioConverter::toDTO).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получение всего аудио одного автора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио одного автора получено",response = AudioDto.class, responseContainer = "List")})
    @GetMapping(value = "/author/{author}")
    public ResponseEntity<List<AudioDto>> getAudioOfAuthor(@ApiParam(value = "Имя исполнителя", example = "Blur")@PathVariable @NotNull String author) {
        logger.info(String.format("Отправка всего аудио автора %s", author));
        return ResponseEntity.ok().body(audioServiceDto.getAudioOfAuthor(author));
    }


    @ApiOperation(value = "Получение  аудио по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио по названию получено",response = AudioDto.class)})
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<AudioDto> getAudioOfName(@ApiParam(value = "Название аудио", example = "Song2")@PathVariable @NotNull String name) {
        logger.info(String.format("Отправка всего аудио автора %s", name));
        return ResponseEntity.ok().body(audioServiceDto.getAudioOfName(name));
    }

    @ApiOperation(value = "Получение всего аудио одного альбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио одного альбома получено",response = AudioDto.class,responseContainer = "List")})
    @GetMapping(value = "/album/{album}")
    public ResponseEntity<List<AudioDto>> getAudioOfAlbum(@ApiParam(value = "Название альбома", example = "The best")@PathVariable @NotNull String album) {
        logger.info(String.format("Отправка всего аудио альбома %s", album));
        return ResponseEntity.ok().body(audioServiceDto.getAudioOfAlbum(album));
    }

    @ApiOperation(value = "Получение всего аудио из коллекции пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио из коллекции пользователя",response = AudioDto.class, responseContainer = "List")})
    @GetMapping(value = "/user/")
    public ResponseEntity<List<AudioDto>> getAudioOfUser() {
        logger.info(String.format("Отправка всего аудио пользователя %s", 60L));
        return ResponseEntity.ok().body(audioServiceDto.getAudioOfUser(60L));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя по частям",responseContainer = "List",response = AudioDto.class)})
    @GetMapping(value = "/PartAudioOfUser")
    public ResponseEntity<List<AudioDto>> getPartAudioOfUser(@ApiParam(value = "Текущая страница", example = "1")@RequestParam("currentPage") int currentPage,
                                                             @ApiParam(value = "Количество данных на страницу", example = "15")@RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Аудио пользователя %s начиная c объекта номер %s, в количестве %s отправлено ", 60L, (currentPage - 1) * itemsOnPage + 1, itemsOnPage));
        return ResponseEntity.ok().body(audioServiceDto.getPartAudioOfUser(60L, currentPage, itemsOnPage));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по автору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя по автору", response = AudioDto.class, responseContainer = "List")})
    @GetMapping(value = "/AuthorAudioOfUser")
    public ResponseEntity<List<AudioDto>> getAuthorAudioOfUser(@ApiParam(value = "Имя исполнителя", example = "Blur")@RequestParam("author") String author) {
        logger.info(String.format("Отправка избранного аудио пользователя c id %s автора %s", 60L, author));
        return ResponseEntity.ok().body(audioServiceDto.getAuthorAudioOfUser(60L, author));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по альбому")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио из коллекции пользователя по альбому",response = AudioDto.class,responseContainer = "List")})
    @GetMapping(value = "/AlbumAudioOfUser")
    public ResponseEntity<List<AudioDto>> getAlbumAudioOfUser(@ApiParam(value = "Название альбома",example = "My Album")@RequestParam("album") String album) {
        logger.info(String.format("Отправка избранного аудио пользователя c id %s альбома %s", 60L, album));
        return ResponseEntity.ok().body(audioServiceDto.getAlbumAudioOfUser(60L, album));
    }

    @ApiOperation(value = "Добавление аудио в избранное пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио успешно добавлено")})
    @PostMapping(value = "/addToUser")
    public ResponseEntity<?> addAudioInCollectionsOfUser(@ApiParam(value = "Id музыке",example = "153")@RequestParam("audioId") Long audioId) {
        if (audioServiceDto.addAudioInCollectionsOfUser(60L, audioId)) {
            logger.info(String.format("Успешное добавление аудио с id %s в избранное пользователю с id %s", audioId, 60L));
            return ResponseEntity.ok().body("Успешно");
        } else {
            return ResponseEntity.ok().body(String.format("Неудачное добавление аудио с id %s в избранное пользователю с id %s", audioId, 60L));
        }
    }

    @ApiOperation(value = "Добавление аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио успешно добавлено", response = AudioDto.class)})
    @PostMapping(value = "/add")
    public ResponseEntity<?> addAudio(@ApiParam(value = "Объект добавляемого аудио")@RequestBody @Valid @NonNull AudioDto audioDto) {
        User user = userService.getById(60L);
        Audios audios = audioConverter.toAudio(audioDto, MediaType.AUDIO, user);
        audiosService.create(audios);
        logger.info(String.format("Добавление аудио с id %s в бд", audioDto.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(audioDto);
    }

    @ApiOperation(value = "Получение всеч альбомов пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Альбомы успешно получены", response = AlbumDto.class,responseContainer = "List"),
            @ApiResponse(code = 400, message = "Альбомы не найдены")
    })
    @GetMapping(value = "/getAllAlbumsFromUser")
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        logger.info(String.format("Получение всех альбомов пользователя с id %s", 60L));
        return ResponseEntity.ok().body(albumServiceDto.getAlbumOfUser(60L));
    }


    @ApiOperation(value = "Добавить аудио в альбом")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио в альбом успешно добавлено", response = String.class)})
    @PostMapping(value = "/addInAlbums")
    public ResponseEntity<?> addInAlbums(@ApiParam(value = "Id альбома",example = "242")@RequestParam @Valid @NotNull Long albumId,
                                         @ApiParam(value = "Id альбома",example = "242")@RequestParam @NotNull Long audioId) {
        logger.info(String.format("Аудио с id  %s добавлено в альбом с id %s", audioId, albumId));
        AlbumAudios albumAudios = albumAudioService.getById(albumId);
        Set<Audios> audiosSet = albumAudios.getAudios();
        audiosSet.add(audiosService.getById(audioId));
        albumAudios.setAudios(audiosSet);
        albumAudioService.create(albumAudios);
        return ResponseEntity.ok().body(String.format("Аудио с id  %s добавлено в альбом с id %s", audioId, albumId));
    }

    @ApiOperation(value = "Создание альбома пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Аудио в альбом успешно добавлено", response = AlbumDto.class),
            @ApiResponse(code = 400, message = "Неверные параметры", response = String.class)})
    @Validated(OnCreate.class)
    @PostMapping(value = "/createAlbum")
    public ResponseEntity<?> createAlbum(@ApiParam(value = "объект создаваемого альбома")@RequestBody @NotNull @Valid AlbumDto albumDto) {
        AlbumDto albumDtoResponse = albumServiceDto.createAlbum(albumDto, 60L);
        logger.info(String.format("Альбом с именем  %s создан", albumDto.getName()));
        return ResponseEntity.ok().body(albumDtoResponse);
    }

    @ApiOperation(value = "Получение всех аудио из альбома пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из альбома пользователя успешно получено", response = AudioDto.class,responseContainer = "List")})
    @GetMapping(value = "/getFromAlbumOfUser")
    public ResponseEntity<?> getFromAlbumOfUser(@ApiParam(value = "Id альбома", example = "Blur:The Best of")@RequestParam @NotNull Long albumId) {
        logger.info(String.format("Все аудио из альбома с id:%s отправлено", albumId));
        return ResponseEntity.ok().body(audioServiceDto.getAudioFromAlbomOfUser(albumId));
    }

}
