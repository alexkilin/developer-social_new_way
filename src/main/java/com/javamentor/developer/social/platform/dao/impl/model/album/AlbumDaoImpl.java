package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.UserHasAlbum;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AlbumDaoImpl extends GenericDaoAbstract<Album, Long> implements AlbumDao {

    @PersistenceContext
    private EntityManager entityManager;

    public AlbumDaoImpl() {
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
