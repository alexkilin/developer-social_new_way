package com.javamentor.developer.social.platform.dao.abstracts.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;

public interface PlaylistDao extends GenericDao<Playlist, Long> {
    boolean userHasPlaylist(Long userId, Long playlistId);
}
