package com.javamentor.developer.social.platform.dao.impl.dto.page.image;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getImagesFromAlbumById")
public class PaginationGetAllByAlbumIdDaoImpl implements PaginationDao<ImageDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAllByAlbumIdDaoImpl() {
    }

    @Override
    public List<ImageDto> getItems(Map<String, Object> parameters) {
        long albumId = (Long) parameters.get("albumId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        Query<ImageDto> query = (Query<ImageDto>) entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.media.image.ImageDto(" +
                        "im.id, " +
                        "im.media.url, " +
                        "im.description, " +
                        "im.media.persistDateTime) " +
                        "FROM Image im " +
                        "WHERE im.media.album.id = :albumId " +
                        "ORDER BY im.media.persistDateTime ASC", ImageDto.class)
                .setParameter("albumId", albumId)
                .setFirstResult(currentPage)
                .setMaxResults(itemsOnPage);
        return query.getResultList();
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
