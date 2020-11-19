package com.javamentor.developer.social.platform.dao.impl.dto.page;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public class PageDtoGetAudioOfNameDaoImpl implements PageDtoDao <AudioDto> {
    private final AudioDtoDao audioDtoDao;

    @Autowired
    public PageDtoGetAudioOfNameDaoImpl(AudioDtoDao audioDtoDao){
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    public List<AudioDto> getItems(Map<String, Object> parameters, int currentPage, int itemsOnPage) {
        return audioDtoDao.getAudioOfName((String)parameters.get("name"), currentPage, itemsOnPage);
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return null;
    }
}
