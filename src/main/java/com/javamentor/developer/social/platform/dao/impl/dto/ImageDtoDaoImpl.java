package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class ImageDtoDaoImpl implements ImageDtoDao {

    private final EntityManager entityManager;

    @Autowired
    public ImageDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
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
