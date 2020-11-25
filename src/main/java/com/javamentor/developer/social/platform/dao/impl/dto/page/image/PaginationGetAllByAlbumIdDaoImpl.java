package com.javamentor.developer.social.platform.dao.impl.dto.page.image;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getImagesFromAlbumById")
public class PaginationGetAllByAlbumIdDaoImpl implements PaginationDao<ImageDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final ImageDtoDao imageDtoDao;

    @Autowired
    public PaginationGetAllByAlbumIdDaoImpl(ImageDtoDao imageDtoDao) {
        this.imageDtoDao = imageDtoDao;
    }

    @Override
    public List<ImageDto> getItems(Map<String, Object> parameters) {
        return imageDtoDao.getAllByAlbumId((Long) parameters.get("albumId"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (im) FROM Image im where im.media.album.id = :albumId ",
                Long.class
        ).setParameter("albumId", parameters.get("albumId"))
                .getSingleResult();
    }
}
