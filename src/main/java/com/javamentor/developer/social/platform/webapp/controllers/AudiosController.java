package com.javamentor.developer.social.platform.webapp.controllers;

import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.AudioServiceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(value = "/api/audios", produces = "application/json")
@Api(value = "AudiosApi", description = "Операции с всеми аудиозаписями(получение, сортировка, добавление)")
public class AudiosController {

    private final AudioServiceDto audioServiceDto;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AudiosController(AudioServiceDto audioServiceDto) {
        this.audioServiceDto = audioServiceDto;
    }

    @ApiOperation(value = "Получение всего аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио полученно")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<Optional<List<AudioDto>>> getAllAudios() {
        Optional<List<AudioDto>> list = audioServiceDto.getAllAudios();
        logger.info("Отправка всех аудио  записей");
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение некоторого количества объектов аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "несколько аудио полученно")})
    @GetMapping(value = "/getPart")
    public ResponseEntity<Optional<List<AudioDto>>> getPartAudios(@RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage) {
        Optional<List<AudioDto>> list = audioServiceDto.getPartAudio(currentPage, itemsOnPage);
        logger.info("Отправка некоторого количества объектов аудио");
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение всего аудио одного автора")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио одного автора полученно")})
    @GetMapping(value = "/author/{author}")
    public ResponseEntity<Optional<List<AudioDto>>> getAudioOfAuthor(@PathVariable @NotNull String author) {
        Optional<List<AudioDto>> list = audioServiceDto.getAudioOfAuthor(author);
            logger.info("Отправка всего аудио автора "+author);
            return ResponseEntity.ok().body(list);
    }


    @ApiOperation(value = "Получение  аудио по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио по названию полученно")})
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<Optional<AudioDto>> getAudioOfName(@PathVariable @NotNull String name) {
        Optional<AudioDto> audio = audioServiceDto.getAudioOfName(name);
            logger.info("Отправка всего аудио автора "+name);
            return ResponseEntity.ok().body(audio);
    }

    @ApiOperation(value = "Получение всего аудио одного альбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио одного альбома полученно")})
    @GetMapping(value = "/album/{album}")
    public ResponseEntity<Optional<List<AudioDto>>> getAudioOfAlbum(@PathVariable @NotNull String album) {
        Optional<List<AudioDto>> list = audioServiceDto.getAudioOfAlbum(album);
            logger.info("Отправка всего аудио автора "+album);
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение всего аудио из коллекции пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио из коллекции пользователя")})
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<Optional<List<AudioDto>>> getAudioOfUser(@PathVariable @NotNull Long userId) {
        Optional<List<AudioDto>> list = audioServiceDto.getAudioOfUser(userId);
            logger.info("Отправка всего аудио пользователя "+userId);
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по частям")})
    @GetMapping(value = "/PartAudioOfUser/{userId}")
    public ResponseEntity<Optional<List<AudioDto>>> getPartAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage) {
        Optional<List<AudioDto>> list = audioServiceDto.getPartAudioOfUser(userId, currentPage, itemsOnPage);
            logger.info("Отправка аудио пользователя "+userId +" по частям");
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по автору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по автору")})
    @GetMapping(value = "/AuthorAudioOfUser/{userId}")
    public ResponseEntity<Optional<List<AudioDto>>> getAuthorAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("author") String author) {
        Optional<List<AudioDto>> list = audioServiceDto.getAuthorAudioOfUser(userId, author);
            logger.info("Отправка избранного аудио пользователя c id "+userId + " автора "+ author);
            return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по альбому")  //
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по альбому")})
    @GetMapping(value = "/AlbumAudioOfUser/{userId}")
    public ResponseEntity<Optional<List<AudioDto>>> getAlbumAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("album") String album) {
        Optional<List<AudioDto>> list = audioServiceDto.getAlbumAudioOfUser(userId, album);
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
}
