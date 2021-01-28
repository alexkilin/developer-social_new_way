package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.PlaylistDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class PlaylistDaoImpl extends GenericDaoAbstract<Playlist, Long> implements PlaylistDao {

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
    public Optional<Playlist> getPlaylistByNameAndUserId (long userId, String playlistName) {

            TypedQuery<Playlist> query = entityManager.createQuery("SELECT p FROM Playlist p " +
                    "WHERE p.name = :playlistName " +
                    "AND p.ownerUser.userId = :userId", Playlist.class)
                    .setParameter("playlistName", playlistName)
                    .setParameter("userId", userId);

        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public Optional<Playlist> getByIdWithContent(Long id) {
        TypedQuery<Playlist> query = entityManager.createQuery("SELECT p " +
                        "FROM Playlist AS p " +
                        "LEFT JOIN FETCH p.playlistContent AS c " +
                        "LEFT JOIN FETCH c.media " +
                        "WHERE p.id = :paramId", Playlist.class)
                .setParameter("paramId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}