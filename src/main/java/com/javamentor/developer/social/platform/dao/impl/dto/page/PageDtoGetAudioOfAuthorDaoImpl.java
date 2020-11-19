package com.javamentor.developer.social.platform.dao.impl.dto.page;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class PageDtoGetAudioOfAuthorDaoImpl implements PageDtoDao <AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private AudioDtoDao audioDtoDao;

    @Autowired
    public PageDtoGetAudioOfAuthorDaoImpl(AudioDtoDao audioDtoDao){
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    public List <AudioDto> getItems(Map<String, Object> parameters, int currentPage, int itemsOnPage) {
        return audioDtoDao.getAudioOfAuthor((String) parameters.get("author"), currentPage, itemsOnPage);
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (c) from Audios as c where c.author = :author",
                Long.class
        ).setParameter("author", parameters.get("author"))
                .getSingleResult();
    }
}
