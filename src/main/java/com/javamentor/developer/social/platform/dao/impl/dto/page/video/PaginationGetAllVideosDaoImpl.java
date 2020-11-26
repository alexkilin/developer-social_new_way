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

@Component("getPartVideos")
public class PaginationGetAllVideosDaoImpl implements PaginationDao<VideoDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAllVideosDaoImpl() {
    }

    @Override
    public List<VideoDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.media.video.VideoDto(v.id," +
                        "v.media.url, v.name, v.icon, v.author, v.media.persistDateTime)" +
                        "FROM Videos as v WHERE v.media.mediaType = 2", VideoDto.class)
                .setFirstResult((currentPage - 1)* itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (v) FROM Videos v ",
                Long.class
        ).getSingleResult();
    }
}
