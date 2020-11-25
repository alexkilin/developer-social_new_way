package com.javamentor.developer.social.platform.dao.impl.dto.page.audio;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAudioFromPlaylist")
public class PaginationGetAudioFromPlaylistDaoImpl implements PaginationDao<AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final AudioDtoDao audioDtoDao;

    @Autowired
    public PaginationGetAudioFromPlaylistDaoImpl(AudioDtoDao audioDtoDao) {
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        return audioDtoDao.getAudioFromPlaylist((Long) parameters.get("playlistId"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (au) from Audios au join au.playlists as pl where pl.id = :playlistId",
                Long.class
        ).setParameter("playlistId", parameters.get("playlistId"))
                .getSingleResult();
    }
}
