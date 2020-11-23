package com.javamentor.developer.social.platform.dao.impl.dto.page.pageAlbumsImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumAudioDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AlbumAudioDto;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllAlbumAudio")
public class PageDtoAlbumAudioDaoImpl implements PageDtoDao<AlbumAudioDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final AlbumAudioDtoDao albumAudioDtoDao;

    @Autowired
    public PageDtoAlbumAudioDaoImpl(AlbumAudioDtoDao albumAudioDtoDao) {
        this.albumAudioDtoDao = albumAudioDtoDao;
    }

    @Override
    public List<AlbumAudioDto> getItems(Map<String, Object> parameters) {
        return albumAudioDtoDao.getAllByUserId((Long) parameters.get("userId"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (a) from Album a " +
                        "WHERE a.mediaType = :type " +
                        "AND a.userOwnerId.userId = :userId ",
                Long.class
        ).setParameter("userId", parameters.get("userId"))
                .setParameter("type", MediaType.AUDIO)
                .getSingleResult();
    }
}
