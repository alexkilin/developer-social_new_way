package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.dto.AlbumServiceDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioServiceDto;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AudiosService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.webapp.converters.AudioConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import java.util.Set;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping(value = "/api/audios", produces = "application/json")
@Api(value = "AudiosApi", description = "Операции с всеми аудиозаписями(получение, сортировка, добавление)")
public class AudiosController {

    private final AudioServiceDto audioServiceDto;
    private final AudiosService audiosService;
    private final AudioConverter audioConverter;
    private final UserService userService;
    private final AlbumServiceDto albumServiceDto;
    private final AlbumAudioService albumAudioService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AudiosController(AudioServiceDto audioServiceDto, AudiosService audiosService, AudioConverter audioConverter, UserService userService, AlbumServiceDto albumServiceDto, AlbumAudioService albumAudioService) {
        this.audioServiceDto = audioServiceDto;
        this.audiosService = audiosService;
        this.audioConverter = audioConverter;
        this.userService = userService;
        this.albumServiceDto = albumServiceDto;
        this.albumAudioService = albumAudioService;
    }

    @ApiOperation(value = "Получение всего аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио получено")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<AudioDto>> getAllAudios() {
        logger.info("Отправка всех аудио  записей");
        return ResponseEntity.ok().body(audiosService.getAll().stream().map(audioConverter::toDTO).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получение некоторого количества аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "несколько аудио получено")})
    @GetMapping(value = "/getPart")
    public ResponseEntity<List<AudioDto>> getPartAudios(@RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Аудио начиная  c объекта номер %s , в количестве %s отправлено", currentPage, itemsOnPage));
        return ResponseEntity.ok().body(audiosService.getPart(currentPage, itemsOnPage).stream().map(audioConverter::toDTO).collect(Collectors.toList()));
    }

    @ApiOperation(value = "Получение всего аудио одного автора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио одного автора получено")})
    @GetMapping(value = "/author/{author}")
    public ResponseEntity<List<AudioDto>> getAudioOfAuthor(@PathVariable @NotNull String author) {
        logger.info(String.format("Отправка всего аудио автора %s", author));
        return ResponseEntity.ok().body(audioServiceDto.getAudioOfAuthor(author));
    }


    @ApiOperation(value = "Получение  аудио по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио по названию получено")})
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<AudioDto> getAudioOfName(@PathVariable @NotNull String name) {
        logger.info(String.format("Отправка всего аудио автора %s", name));
        return ResponseEntity.ok().body(audioServiceDto.getAudioOfName(name));
    }

    @ApiOperation(value = "Получение всего аудио одного альбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио одного альбома получено")})
    @GetMapping(value = "/album/{album}")
    public ResponseEntity<List<AudioDto>> getAudioOfAlbum(@PathVariable @NotNull String album) {
        logger.info(String.format("Отправка всего аудио автора %s", album));
        return ResponseEntity.ok().body(audioServiceDto.getAudioOfAlbum(album));
    }

    @ApiOperation(value = "Получение всего аудио из коллекции пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио из коллекции пользователя")})
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<AudioDto>> getAudioOfUser(@PathVariable @NotNull Long userId) {
        logger.info(String.format("Отправка всего аудио пользователя %s", userId));
        return ResponseEntity.ok().body(audioServiceDto.getAudioOfUser(userId));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по частям")})
    @GetMapping(value = "/PartAudioOfUser/{userId}")
    public ResponseEntity<List<AudioDto>> getPartAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage) {
        logger.info(String.format("Аудио начиная  c объекта номер %s , в количестве %s отправлено пользователя %s", currentPage, itemsOnPage, userId));
        return ResponseEntity.ok().body(audioServiceDto.getPartAudioOfUser(userId, currentPage, itemsOnPage));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по автору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по автору")})
    @GetMapping(value = "/AuthorAudioOfUser/{userId}")
    public ResponseEntity<List<AudioDto>> getAuthorAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("author") String author) {
        logger.info(String.format("Отправка избранного аудио пользователя c id %s автора %s", userId, author));
        return ResponseEntity.ok().body(audioServiceDto.getAuthorAudioOfUser(userId, author));
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по альбому")  //
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по альбому")})
    @GetMapping(value = "/AlbumAudioOfUser/{userId}")
    public ResponseEntity<List<AudioDto>> getAlbumAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("album") String album) {
        logger.info(String.format("Отправка избранного аудио пользователя c id %s альбома %s", userId, album));
        return ResponseEntity.ok().body(audioServiceDto.getAlbumAudioOfUser(userId, album));
    }

    @ApiOperation(value = "Добавление аудио в избранное пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио успешно добавлено")})
    @PostMapping(value = "/addToUser")
    public ResponseEntity<?> addAudioInCollectionsOfUser(@RequestParam("audioId") Long audioId, @RequestParam("userId") Long userId) {
        if (audioServiceDto.addAudioInCollectionsOfUser(userId, audioId)) {
            logger.info(String.format("Успешное добавление аудио с id %s  в избранное пользователю с id  %s", audioId, userId));
            return ResponseEntity.ok().body("Успешно");
        } else {
            return ResponseEntity.ok().body(String.format("Неудачное добавление аудио с id %s пользователю с id %s", audioId, userId));
        }
    }

    @ApiOperation(value = "Добавление аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио успешно добавлено")})
    @PostMapping(value = "/add")
    public ResponseEntity<?> addAudio(@RequestBody @Valid @NonNull AudioDto audioDto, @NotNull Long userId) {
        User user = userService.getById(userId);
        Audios audios = audioConverter.toAudio(audioDto, MediaType.AUDIO, user);
        audiosService.create(audios);
        logger.info(String.format("Добавление аудио в бд с id %s", audioDto.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(audioDto);
    }

    @ApiOperation(value = "Получить все альбомы пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "альбомы успешно получены"),
            @ApiResponse(code = 400, message = "альбомы не найдены")
    })
    @GetMapping(value = "/getAllAlbumsFromUser")
    public ResponseEntity<?> getAllAlbums(@RequestParam @Valid @NotNull Long userId) {
        logger.info(String.format("Получение всех альбомов пользователя с id %s", userId));
        return ResponseEntity.ok().body(albumServiceDto.getAlbumOfUser(userId));
    }


    @ApiOperation(value = "Добавить аудио в альбом")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио в альбом успешно добавлено")})
    @PostMapping(value = "/addInAlbums")
    public ResponseEntity<?> addInAlbums(@RequestParam @Valid @NotNull Long albumId, @NotNull Long audioId) {
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
            @ApiResponse(code = 200, message = "аудио в альбом успешно добавлено")})
    @PostMapping(value = "/createAlbum")
    public ResponseEntity<?> createAlbum(@RequestParam @Valid @NotNull String nameOfAlbum, @NotNull String icon, @NotNull Long userId) {
        logger.info(String.format("Альбом с именем  %s создан", nameOfAlbum));
        albumServiceDto.createAlbum(nameOfAlbum, icon, userId);
        return ResponseEntity.ok().body(String.format("Альбом с именем  %s создан", nameOfAlbum));
    }

    @ApiOperation(value = "Получение всего аудио альбома пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из альбома пользователя успешно получено")})
    @GetMapping(value = "/getFromAlbumOfUser")
    public ResponseEntity<?> getFromAlbumOfUser(@RequestParam  @NotNull Long albumId) {
        logger.info(String.format("Все аудио из альбома с id:%s отправлено", albumId));
        return ResponseEntity.ok().body(audioServiceDto.getAudioFromAlbomOfUser(albumId));
    }

}
