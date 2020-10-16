package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumAudioDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumAudioServiceImpl extends GenericServiceAbstract<AlbumAudios, Long> implements AlbumAudioService {

    AlbumAudioDao albumAudioDAO;

    @Autowired
    public AlbumAudioServiceImpl(AlbumAudioDao dao) {
        super(dao);
        this.albumAudioDAO = dao;
    }

    @Override
    public AlbumAudios createAlbumAudiosWithOwner(AlbumAudios albumAudios) {
        return albumAudioDAO.createAlbumAudiosWithOwner(albumAudios);
    }
}
