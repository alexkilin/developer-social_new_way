package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.PlaylistDao;
import com.javamentor.developer.social.platform.models.entity.media.Playlist;
import com.javamentor.developer.social.platform.service.abstracts.model.media.PlaylistService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaylistServiceImpl extends GenericServiceAbstract<Playlist, Long> implements PlaylistService {
    private final PlaylistDao playlistDao;

    @Autowired
    public PlaylistServiceImpl(PlaylistDao dao) {
        super(dao);
        this.playlistDao = dao;
    }

    @Override
    public boolean userHasPlaylist(Long userId, Long playlistId) {
        return playlistDao.userHasPlaylist(userId, playlistId);
    }

    @Override
    public Optional<Playlist> getOptionalById(Long id) {
        return getById(id);
    }
}
