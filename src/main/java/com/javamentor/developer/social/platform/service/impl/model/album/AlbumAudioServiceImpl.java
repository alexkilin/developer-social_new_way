package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumAudioDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AlbumAudioServiceImpl extends GenericServiceAbstract<AlbumAudios, Long> implements AlbumAudioService {

    private final AlbumAudioDao albumAudioDao;

    @Autowired
    public AlbumAudioServiceImpl(AlbumAudioDao dao) {
        super(dao);
        this.albumAudioDao = dao;
    }

    @Override
    @Transactional
    public AlbumAudios createAlbumAudiosWithOwner(AlbumAudios albumAudios) {
        return albumAudioDao.createAlbumAudiosWithOwner(albumAudios);
    }

    @Override
    @Transactional
    public Optional<AlbumAudios> getByIdWithAudios(Long id) {
        return albumAudioDao.getByIdWithAudios(id);
    }
}
