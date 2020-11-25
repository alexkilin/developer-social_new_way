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

@Component("getFromAlbumOfUser")
public class PaginationGetAudioFromAlbumOfUserDaoImpl implements PaginationDao<AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final AudioDtoDao audioDtoDao;

    @Autowired
    public PaginationGetAudioFromAlbumOfUserDaoImpl(AudioDtoDao audioDtoDao){
        this.audioDtoDao = audioDtoDao;
    }

    @Override
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        return audioDtoDao.getAudioFromAlbumOfUser((Long) parameters.get("albumId"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (c) from AlbumAudios u join u.audios as c where u.album.id =:albumId",
                Long.class
        ).setParameter("albumId", parameters.get("albumId"))
                .getSingleResult();
    }
}
