package com.javamentor.developer.social.platform.dao.impl.dto.page.video;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getVideoOfNamePart")
public class PaginationGetVideoOfNamePartDaoImpl implements PaginationDao<VideoDto> {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetVideoOfNamePartDaoImpl() {
    }

    @Override
    public List<VideoDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.media.video.VideoDto(v.id, " +
                        "v.media.url, v.name, v.icon, v.author, v.media.persistDateTime, count (ml.like.id)) " +
                        "FROM Videos AS v LEFT JOIN MediaLike AS ml ON v.media.id = ml.media.id " +
                        "WHERE v.name LIKE :searchPattern GROUP BY v.media.id, v.media.url, v.media.persistDateTime " +
                        "ORDER BY v.id ASC", VideoDto.class)
                .setParameter("searchPattern", "%" + parameters.get("namePart") + "%")
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "SELECT count(v) FROM Videos AS v " +
                        "WHERE v.name LIKE :searchPattern", Long.class)
                .setParameter("searchPattern", "%" + parameters.get("namePart") + "%")
                .getSingleResult();
    }
}
