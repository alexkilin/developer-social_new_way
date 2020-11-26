package com.javamentor.developer.social.platform.dao.impl.dto.page.albums;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllImageAlbumsOfUser")
public class PaginationAlbumImageDaoImpl implements PaginationDao<AlbumImageDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationAlbumImageDaoImpl() {
    }

    @Override
    public List<AlbumImageDto> getItems(Map<String, Object> parameters) {
        Long userId = (Long) parameters.get("userId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        return entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto(" +
                        "a.id, " +
                        "a.name, " +
                        "a.icon," +
                        "a.persistDate," +
                        "a.lastRedactionDate) " +
                        "FROM Album a " +
                        "WHERE a.mediaType = :type " +
                        "AND a.userOwnerId.userId = :id " +
                        "ORDER BY a.id ASC", AlbumImageDto.class)
                .setParameter("type", MediaType.IMAGE)
                .setParameter("id", userId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (a) from Album a " +
                        "WHERE a.mediaType = :type " +
                        "AND a.userOwnerId.userId = :userId ",
                Long.class
        ).setParameter("userId", parameters.get("userId"))
                .setParameter("type", MediaType.IMAGE)
                .getSingleResult();
    }
}
