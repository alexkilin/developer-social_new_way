package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDTODAO;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.ImageDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ImageDTODAOImpl implements ImageDTODAO {

    private final EntityManager entityManager;

    @Autowired
    public ImageDTODAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<ImageDto> getAllByUserId(int offset, int limit, Long id) {
        Query<ImageDto> query = (Query<ImageDto>) entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.ImageDto(" +
                        "im.id, " +
                        "im.media.url, " +
                        "im.description, " +
                        "im.media.persistDateTime) " +
                        "FROM Image im " +
                        "WHERE im.media.user.userId = :userId " +
                        "ORDER BY im.media.persistDateTime ASC", ImageDto.class)
                .setParameter("userId", id)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return query.getResultList();
    }

    //TODO переписать
    @Override
    public List<ImageDto> getAllByAlbumId(int offset, int limit, Long id) {
        Query<ImageDto> query = (Query<ImageDto>) entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.ImageDto(" +
                        "im.id, " +
                        "im.media.url, " +
                        "im.description, " +
                        "im.media.persistDateTime) " +
                        "FROM Image im " +
                        "WHERE im.album = :userId " +
                        "ORDER BY im.media.persistDateTime ASC", ImageDto.class)
                .setParameter("userId", id)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return null;
    }

    @Override
    public Optional<ImageDto> getById(Long id) {
        Query<ImageDto> query = (Query<ImageDto>) entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.ImageDto(" +
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
