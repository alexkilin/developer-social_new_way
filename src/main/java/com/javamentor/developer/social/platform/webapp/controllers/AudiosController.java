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
    public ResponseEntity<?> getAudioOfAuthor(@PathVariable @NotNull String author) {
        List<AudioDto> list = audioServiceDto.getAudioOfAuthor(author);
        if (list.size()!=0){
            logger.info("Отправка всего аудио автора "+author);
            return ResponseEntity.ok().body(list);
        }
        logger.error("Запрос аудио у не существующего автора");
        return ResponseEntity.ok().body("Данный автор не найден");
    }

    @ApiOperation(value = "Получение  аудио по названию")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио по названию полученно")})
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> getAudioOfName(@PathVariable @NotNull String name) {
        AudioDto audio = audioServiceDto.getAudioOfName(name);
        if (audio.getId()!=null){
            logger.info("Отправка всего аудио автора "+name);
            return ResponseEntity.ok().body(audio);
        }
        logger.error("Запрос аудио у по не существующему названию");
        return ResponseEntity.ok().body("Аудио с данным названием не найдено");
    }

    @ApiOperation(value = "Получение всего аудио одного альбома")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио одного альбома полученно")})
    @GetMapping(value = "/album/{album}")
    public ResponseEntity<?> getAudioOfAlbum(@PathVariable @NotNull String album) {
        List<AudioDto> list = audioServiceDto.getAudioOfAlbum(album);
        if (list.size()!=0){
            logger.info("Отправка всего аудио автора "+album);
            return ResponseEntity.ok().body(list);
        }
        logger.error("Запрос аудио у не существующего album");
        return ResponseEntity.ok().body("Данный album не найден");
    }

    @ApiOperation(value = "Получение всего аудио из коллекции пользователя")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "все аудио из коллекции пользователя")})
    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<?> getAudioOfUser(@PathVariable @NotNull Long userId) {
        List<AudioDto> list = audioServiceDto.getAudioOfUser(userId);
        if (list.size()!=0){
            logger.info("Отправка всего аудио пользователя "+userId);
            return ResponseEntity.ok().body(list);
        }
        logger.error("Запрос аудио у не существующего пользователя");
        return ResponseEntity.ok().body("Данный пользователь не найден, или нет музыки и у данного пользователя");
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по частям")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по частям")})
    @GetMapping(value = "/PartAudioOfUser/{userId}")
    public ResponseEntity<?> getPartAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("currentPage") int currentPage, @RequestParam("itemsOnPage") int itemsOnPage) {
        List<AudioDto> list = audioServiceDto.getPartAudioOfUser(userId, currentPage, itemsOnPage);
        if (list.size()!=0){
            logger.info("Отправка аудио пользователя "+userId +" по частям");
            return ResponseEntity.ok().body(list);
        }
        logger.error("Запрос аудио у не существующего пользователя");
        return ResponseEntity.ok().body("Данный пользователь не найден, или нет музыки и у данного пользователя в указанном диапазоне");
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по автору")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по автору")})
    @GetMapping(value = "/AuthorAudioOfUser/{userId}")
    public ResponseEntity<?> getAuthorAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("author") String author) {
        List<AudioDto> list = audioServiceDto.getAuthorAudioOfUser(userId, author);
        if (list.size()!=0){
            logger.info("Отправка избранного аудио пользователя c id "+userId + " автора "+ author);
            return ResponseEntity.ok().body(list);
        }
        logger.error("Запрос избранного аудио пользователя c id "+userId + " автора "+ author +"НЕУДАЧНО");
        return ResponseEntity.ok().body("Данный пользователь не найден, или нет музыки и у данного пользователя с указанным автором");
    }

    @ApiOperation(value = "Получение аудио из коллекции пользователя по альбому")  //
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "аудио из коллекции пользователя по альбому")})
    @GetMapping(value = "/AlbumAudioOfUser/{userId}")
    public ResponseEntity<?> getAlbumAudioOfUser(@PathVariable @NotNull Long userId, @RequestParam("album") String album) {
        List<AudioDto> list = audioServiceDto.getAlbumAudioOfUser(userId, album);
        if (list.size()!=0){
            logger.info("Отправка избранного аудио пользователя c id "+userId + " альбома "+ album);
            return ResponseEntity.ok().body(list);
        }
        logger.error("Запрос избранного аудио пользователя c id "+userId + " альбома "+ album +"НЕУДАЧНО");
        return ResponseEntity.ok().body("Данный пользователь не найден, или нет музыки и у данного пользователя с указанным альбомом");
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
