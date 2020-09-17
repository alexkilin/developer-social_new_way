package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.PlaylistDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PlaylistDaoImpl extends GenericDaoAbstract<Playlist, Long> implements PlaylistDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean userHasPlaylist(Long userId, Long playlistId) {
        Long count = entityManager.createQuery("SELECT count(p.id) FROM Playlist p " +
                "WHERE p.id = :playlistId " +
                "AND p.ownerUser.userId = :userId", Long.class)
                .setParameter("playlistId", playlistId)
                .setParameter("userId", userId)
                .getSingleResult();
        return count > 0;
    }
}
