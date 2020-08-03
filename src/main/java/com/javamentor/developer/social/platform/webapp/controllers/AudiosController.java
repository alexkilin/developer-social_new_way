package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioServiceDto;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AudiosService;
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

@Validated
@RestController
@RequestMapping(value = "/api/audios", produces = "application/json")
@Api(value = "AudiosApi", description = "Операции с всеми аудиозаписями(получение, сортировка, добавление)")
public class AudiosController {

    private final AudioServiceDto audioServiceDto;
    private final AudiosService audiosService;
    private final AudioConverter audioConverter;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AudiosController(AudioServiceDto audioServiceDto, AudiosService audiosService, AudioConverter audioConverter) {
        this.audioServiceDto = audioServiceDto;
        this.audiosService = audiosService;
        this.audioConverter = audioConverter;
    }

    @ApiOperation(value = "Получение всего аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио полученно")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<AudioDto>> getAllAudios() {
        List<AudioDto> list = audioServiceDto.getAllAudios();
        logger.info("Отправка всех аудио  записей");
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение некоторого количества объектов аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "несколько аудио полученно")})
    @GetMapping(value = "/getPart")
    public ResponseEntity<List<AudioDto>> getPartAudios(@RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage) {
        List<AudioDto> list = audioServiceDto.getPartAudio(currentPage, itemsOnPage);
        logger.info("Отправка некоторого количества объектов аудио");
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение всего аудио одного автора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио одного автора полученно")})
    @GetMapping(value = "/author/{author}")
    public ResponseEntity<List<AudioDto>> getAudioOfAuthor(@PathVariable @NotNull String author) {
        List<AudioDto>  list = audioServiceDto.getAudioOfAuthor(author);
            logger.info("Отправка всего аудио автора "+author);
            return ResponseEntity.ok().body(list);
    }


    @ApiOperation(value = "Получение  аудио по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио по названию полученно")})
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<AudioDto> getAudioOfName(@PathVariable @NotNull String name) {
        AudioDto audio = audioServiceDto.getAudioOfName(name);
            logger.info("Отправка всего аудио автора "+name);
            return ResponseEntity.ok().body(audio);
    }

    @ApiOperation(value = "Получение всего аудио одного альбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио одного альбома полученно")})
    @GetMapping(value = "/album/{album}")
    public ResponseEntity<List<AudioDto>> getAudioOfAlbum(@PathVariable @NotNull String album) {
        List<AudioDto>  list = audioServiceDto.getAudioOfAlbum(album);
            logger.info("Отправка всего аудио автора "+album);
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение всего аудио из коллекции пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио из коллекции пользователя")})
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<List<AudioDto>> getAudioOfUser(@PathVariable @NotNull Long userId) {
        List<AudioDto>  list = audioServiceDto.getAudioOfUser(userId);
            logger.info("Отправка всего аудио пользователя "+userId);
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по частям")})
    @GetMapping(value = "/PartAudioOfUser/{userId}")
    public ResponseEntity<List<AudioDto>> getPartAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage) {
        List<AudioDto>  list = audioServiceDto.getPartAudioOfUser(userId, currentPage, itemsOnPage);
            logger.info("Отправка аудио пользователя "+userId +" по частям");
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по автору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по автору")})
    @GetMapping(value = "/AuthorAudioOfUser/{userId}")
    public ResponseEntity<List<AudioDto>> getAuthorAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("author") String author) {
        List<AudioDto>  list = audioServiceDto.getAuthorAudioOfUser(userId, author);
            logger.info("Отправка избранного аудио пользователя c id "+userId + " автора "+ author);
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по альбому")  //
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по альбому")})
    @GetMapping(value = "/AlbumAudioOfUser/{userId}")
    public ResponseEntity<List<AudioDto>> getAlbumAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("album") String album) {
        List<AudioDto>  list = audioServiceDto.getAlbumAudioOfUser(userId, album);
            logger.info("Отправка избранного аудио пользователя c id "+userId + " альбома "+ album);
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Добавление аудио в избранное пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио успешно добавленно")})
    @GetMapping(value = "/addToUser")
    public ResponseEntity<?> addAudioInCollectionsOfUser(@RequestParam("audioId") Long audioId, @RequestParam("userId") Long userId) {
        if (audioServiceDto.addAudioInCollectionsOfUser(userId, audioId)){
            logger.info("Успешное добавление аудио с id  "+userId + " аудио с id  "+ audioId);
            return ResponseEntity.ok().body("Успешно");
        }
        else {
            return ResponseEntity.ok().body("Неудачное добавление аудио "+audioId+" пользователю "+userId);
        }
    }

    @ApiOperation(value = "Добавление аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио успешно добавленно")})
    @GetMapping(value = "/add")
    public ResponseEntity<?> addAudio(@RequestBody @Valid @NonNull AudioDto audioDto, User user) {
        Audios audios = audioConverter.toAudio(audioDto, MediaType.AUDIO, user);
        audiosService.create(audios);
        logger.info("Добавление аудио в бд "+audioDto.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(audioDto);
    }
}
