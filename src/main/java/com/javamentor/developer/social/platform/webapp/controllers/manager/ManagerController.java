package com.javamentor.developer.social.platform.webapp.controllers.manager;

import com.javamentor.developer.social.platform.service.statistics.StatisticsService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;


@RestController
@RequestMapping(value = "/api/manager/statistics", produces = "application/json")
@SuppressWarnings("deprecation")
@Api(value = "ManagerApi", description = "Операции получения статистики для менеджера")
public class ManagerController {

    private final StatisticsService statisticsService;
    private final StatisticsService professionStatistics;

    public ManagerController(StatisticsService statisticsService, StatisticsService professionStatistics) {
        this.statisticsService = statisticsService;
        this.professionStatistics = professionStatistics;
    }


    @ApiOperation(value = "Получение статистики 'Самые популярные профессии пользователей'")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ссылка на таблицу 'Самые популярные профессии пользователей'", response = String.class)})
    @GetMapping(value = "/popularProfessionsInUsers")

    public ResponseEntity<?> getStatsPopularProfessionsInUsers() throws IOException, GeneralSecurityException {

        String googleSheetRef = professionStatistics.createGoogleSheetMostPopularProfessionsInUsers();

        return ResponseEntity.ok().body(googleSheetRef);
    }

    @ApiOperation(value = "Получение статистики 'Самые популярные теги в постах'")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ссылка на таблицу 'Самые популярные теги в постах'", response = String.class)})
    @GetMapping(value = "/popularTagsInPosts")

    public ResponseEntity <?> getStatsPopularTagsInPosts() throws IOException, GeneralSecurityException {

        String googleSheetRef = statisticsService.createGoogleSheetMostPopularTagsInPosts();

        return ResponseEntity.ok().body(googleSheetRef);
    }
}

