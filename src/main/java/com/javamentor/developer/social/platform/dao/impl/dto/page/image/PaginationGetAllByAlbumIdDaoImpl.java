package com.javamentor.developer.social.platform.dao.impl.dto.page.image;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component("getImagesFromAlbumById")
public class PaginationGetAllByAlbumIdDaoImpl implements PaginationDao<ImageDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAllByAlbumIdDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ImageDto> getItems(Map<String, Object> parameters) {
        long albumId = (Long) parameters.get("albumId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        return entityManager.createQuery(
                "SELECT " +
                        "im.id, " +
                        "im.media.url, " +
                        "im.description, " +
                        "im.media.persistDateTime " +
                        "FROM AlbumImage am " +
                        "JOIN am.images as im " +
                        "WHERE am.id = :albumId ")
                .setParameter("albumId", albumId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return ImageDto.builder()
                                .id((Long)objects[0])
                                .url((String)objects[1])
                                .description((String)objects[2])
                                .persistDateTime((LocalDateTime)objects[3])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }).getResultList();
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
