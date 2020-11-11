package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.AlbumDto;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class AlbumDtoDaoImpl implements AlbumDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    public AlbumDtoDaoImpl() {}

    @Override
    public List<AlbumDto> getAllByTypeAndUserId(MediaType type, Long userId) {
        return entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.media.AlbumDto(" +
                        "a.id, " +
                        "a.name, " +
                        "a.icon) " +
                        "FROM Album a " +
                        "WHERE a.mediaType = :type " +
                        "AND a.userOwnerId.userId = :id " +
                        "ORDER BY a.id ASC", AlbumDto.class)
                .setParameter("type", type)
                .setParameter("id", userId)
                .getResultList();

    }

    @Override
    public Optional<AlbumDto> getById(Long id) {
        Query<AlbumDto> query = (Query<AlbumDto>) entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.media.AlbumDto(" +
                "album.id, " +
                "album.name, " +
                "album.icon) " +
                "FROM Album AS album " +
                "WHERE album.id = :id", AlbumDto.class)
                .setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

}
