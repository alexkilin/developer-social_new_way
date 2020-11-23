package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class ImageDtoDaoImpl implements ImageDtoDao {

    private final EntityManager entityManager;

    @Autowired
    public ImageDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<ImageDto> getAllByUserId(Long userId, int currentPage, int itemsOnPage) {
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
                .setMaxResults(currentPage * itemsOnPage);
        return query.getResultList();
    }

    @Override
    public List<ImageDto> getAllByAlbumId(Long albumId, int currentPage, int itemsOnPage) {
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
                .setMaxResults(currentPage * itemsOnPage);
        return query.getResultList();
    }

    @Override
    public Optional<ImageDto> getById(Long id) {
        Query<ImageDto> query = (Query<ImageDto>) entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.media.image.ImageDto(" +
                        "im.id, " +
                        "im.media.url, " +
                        "im.description, " +
                        "im.media.persistDateTime) " +
                        "FROM Image im " +
                        "WHERE im.id = :id", ImageDto.class)
                .setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

}
