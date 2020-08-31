package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.UserHasAlbum;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.webapp.converters.AlbumConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class AlbumDAOImpl extends GenericDaoAbstract<Album, Long> implements AlbumDAO {

    @PersistenceContext
    EntityManager entityManager;

    AlbumConverter albumConverter;

    @Autowired
    public AlbumDAOImpl(AlbumConverter albumConverter) {
        this.albumConverter = albumConverter;
    }

    @Override
    public boolean existsByNameAndMediaType(String name, MediaType type) {
        Long count = entityManager.createQuery(
                "SELECT count(a) " +
                        "FROM Album AS a " +
                        "WHERE a.name = :name " +
                        "AND a.mediaType = :type", Long.class)
                .setParameter("name", name)
                .setParameter("type", type)
                .getSingleResult();
        return (count > 0);
    }

    @Override
    public void assignAlbumToUser(Album album) {
        entityManager.persist(UserHasAlbum.builder()
                .user(album.getUserOwnerId())
                .album(album)
                .build());
    }
}
