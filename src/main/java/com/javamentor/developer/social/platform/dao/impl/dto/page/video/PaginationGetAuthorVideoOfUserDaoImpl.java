package com.javamentor.developer.social.platform.dao.impl.dto.page.video;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAuthorVideoOfUser")
public class PaginationGetAuthorVideoOfUserDaoImpl implements PaginationDao<VideoDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAuthorVideoOfUserDaoImpl() {

    }

    @Override
    public List<VideoDto> getItems(Map<String, Object> parameters) {
        Long userId = (Long) parameters.get("userId");
        String author = (String) parameters.get("author");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.media.video.VideoDto(v.id," +
                        " v.media.url, v.name, v.icon, v.author, v.media.persistDateTime, count (ml.like.id), v.view)" +
                        " FROM User u join u.videos v left join MediaLike ml on v.media.id = ml.media.id " +
                        "where u.userId =:userId and v.author =:author " +
                        "GROUP BY v.media.id, v.media.url, v.media.persistDateTime " +
                        "ORDER BY v.id ASC", VideoDto.class)
                .setParameter("userId", userId)
                .setParameter("author", author)
                .setFirstResult((currentPage - 1)* itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (v) FROM User u " +
                        "join u.videos v where u.userId =:userId " +
                        "and v.author =:author",
                Long.class
        ).setParameter("userId", parameters.get("userId"))
                .setParameter("author", parameters.get("author"))
                .getSingleResult();
    }
}
