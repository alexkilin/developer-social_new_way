package com.javamentor.developer.social.platform.dao.abstracts.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;

import java.util.Optional;

public interface PlaylistDao extends GenericDao<Playlist, Long> {
    boolean userHasPlaylist(Long userId, Long playlistId);
    Optional<Playlist> getPlaylistByNameAndUserID (long userID, String playlistName);
}
