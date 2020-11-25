package com.javamentor.developer.social.platform.dao.impl.dto.page.playlist;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAudioFromPlaylistById")
public class PaginationByPlaylistIdDaoImpl implements PaginationDao<AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final PlaylistDtoDao playlistDtoDao;

    @Autowired
    public PaginationByPlaylistIdDaoImpl(PlaylistDtoDao playlistDtoDao) {
        this.playlistDtoDao = playlistDtoDao;
    }

    @Override
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        return playlistDtoDao.getAudioDtoByPlaylistId((Long) parameters.get("playlistId"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (c) from Playlist p " +
                        "join p.playlistContent c where p.id = :playlistId",
                Long.class
        ).setParameter("playlistId", parameters.get("playlistId"))
                .getSingleResult();
    }
}

