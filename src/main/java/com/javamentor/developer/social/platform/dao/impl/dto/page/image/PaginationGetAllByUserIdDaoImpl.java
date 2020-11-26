package com.javamentor.developer.social.platform.dao.impl.dto.page.image;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllImagesOfUser")
public class PaginationGetAllByUserIdDaoImpl implements PaginationDao<ImageDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAllByUserIdDaoImpl() {
    }

    @Override
    public List<ImageDto> getItems(Map<String, Object> parameters) {
        Long userId = (Long) parameters.get("userId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        Query<ImageDto> query = (Query<ImageDto>) entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.media.image.ImageDto(" +
                        "im.id, " +
                        "im.media.url, " +
                        "im.description, " +
                        "im.media.persistDateTime) " +
                        "FROM Image im " +
                        "WHERE im.media.user.userId = :userId " +
                        "ORDER BY im.media.persistDateTime ASC", ImageDto.class)
                .setParameter("userId", userId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage);
        return query.getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (im) FROM Image im where im.media.user.userId = :userId ",
                Long.class
        ).setParameter("userId", parameters.get("userId"))
                .getSingleResult();
    }
}
