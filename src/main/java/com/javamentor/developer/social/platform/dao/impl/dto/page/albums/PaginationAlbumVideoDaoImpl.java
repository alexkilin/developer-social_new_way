package com.javamentor.developer.social.platform.dao.impl.dto.page.albums;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumVideoDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.video.AlbumVideoDto;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllVideoAlbums")
public class PaginationAlbumVideoDaoImpl implements PaginationDao<AlbumVideoDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final AlbumVideoDtoDao albumVideoDtoDao;

    @Autowired
    public PaginationAlbumVideoDaoImpl(AlbumVideoDtoDao albumVideoDtoDao) {
        this.albumVideoDtoDao = albumVideoDtoDao;
    }

    @Override
    public List<AlbumVideoDto> getItems(Map<String, Object> parameters) {
        return albumVideoDtoDao.getAllByUserId((Long) parameters.get("userId"),
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
                .setParameter("type", MediaType.VIDEO)
                .getSingleResult();
    }
}
