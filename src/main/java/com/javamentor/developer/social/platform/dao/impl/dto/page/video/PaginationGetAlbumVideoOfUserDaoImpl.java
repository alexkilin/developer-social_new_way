package com.javamentor.developer.social.platform.dao.impl.dto.page.video;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAlbumVideoOfUser")
public class PaginationGetAlbumVideoOfUserDaoImpl implements PaginationDao<VideoDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final VideoDtoDao videoDtoDao;

    @Autowired
    public PaginationGetAlbumVideoOfUserDaoImpl(VideoDtoDao videoDtoDao) {
        this.videoDtoDao = videoDtoDao;
    }

    @Override
    public List<VideoDto> getItems(Map<String, Object> parameters) {
        return videoDtoDao.getAlbumVideoOfUser((Long) parameters.get("userId"),
                (String) parameters.get("album"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (v) from AlbumVideo av " +
                        "join av.videos as v where av.album.name =:album " +
                        "and av.album.userOwnerId.userId = :userId",
                Long.class
        ).setParameter("album", parameters.get("album"))
                .setParameter("userId", parameters.get("userId"))
                .getSingleResult();
    }
}
