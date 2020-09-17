package com.javamentor.developer.social.platform.service.abstracts.model.media;

import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface PlaylistService extends GenericService<Playlist, Long> {
    boolean userHasPlaylist(Long userId, Long playlistId);
}
