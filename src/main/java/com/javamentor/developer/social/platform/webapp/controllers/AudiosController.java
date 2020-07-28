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
    // вот это я так понимаю вообще не нужно, загружать сущности нужно кусочками (метод getPartAudios)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Все аудио полученно")
    })
    @GetMapping(value = "/all")
    public ResponseEntity<List<AudioDto>> getAllAudios() {                        //вот тут я должен делать проверку авторизации пользователя или это все фронты сами там делают?
        List<AudioDto> list = audioServiceDto.getAllAudios();
        logger.info("Отправка всех аудио  записей");
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Получение некоторого количества объектов аудио")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "несколько аудио полученно")})
    @GetMapping(value = "/getPart")
    public ResponseEntity<List<AudioDto>> getPartAudios(@RequestParam("start") int start, @RequestParam("end") int end) {
        List<AudioDto> list = audioServiceDto.getPartAudio(start, end);
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
}
