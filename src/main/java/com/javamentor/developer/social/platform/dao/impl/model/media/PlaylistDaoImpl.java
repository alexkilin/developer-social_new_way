package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.PlaylistDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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

    @Override
    public Optional<Playlist> getPlaylistByNameAndUserID (long userId, String playlistName) {
        Playlist opt;
        try {
            opt = entityManager.createQuery("SELECT p FROM Playlist p " +
                    "WHERE p.name = :playlistName " +
                    "AND p.ownerUser.userId = :userId", Playlist.class)
                    .setParameter("playlistName", playlistName)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (RuntimeException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(opt);
    }
}
