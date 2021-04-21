package com.javamentor.developer.social.platform.service.statistics;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.javamentor.developer.social.platform.dao.abstracts.model.post.TagDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StatisticsService {

    private final GoogleSheetsUtil googleSheetsUtil;
    private final TagDao tagDao;

    @Value("${googleSheet.spreadsheetId}")
    private String spreadsheetId;

    public StatisticsService(GoogleSheetsUtil googleSheetsUtil, TagDao tagDao) {
        this.googleSheetsUtil = googleSheetsUtil;
        this.tagDao = tagDao;
    }

    public String createGoogleSheetMostPopularTagsInPosts() throws IOException, GeneralSecurityException {

        Sheets sheetsService = googleSheetsUtil.getSheets();

        sheetsService.spreadsheets().values().clear(spreadsheetId, "A1:Z1000", new ClearValuesRequest()).execute();

        List<List<Object>> valueRange = new ArrayList<>();
        valueRange.add(Arrays.asList("Tag", "Rate %"));
        tagDao.getMostPopularTagsInPosts().stream().forEach(o->
                valueRange.add(Arrays.asList(o.getTitle(), String.valueOf(o.getRate()))));

        ValueRange body = new ValueRange()
                .setValues(valueRange);

        sheetsService.spreadsheets().values()
                .update(spreadsheetId, "A1", body)
                .setValueInputOption("RAW")
                .execute();

        return "https://docs.google.com/spreadsheets/d/" + spreadsheetId;

    }
}
