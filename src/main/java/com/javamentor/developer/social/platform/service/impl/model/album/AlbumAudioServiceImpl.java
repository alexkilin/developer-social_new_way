package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumAudioDAO;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumAudioServiceImpl extends GenericServiceAbstract<AlbumAudios, Long> implements AlbumAudioService {

    @Autowired
    public AlbumAudioServiceImpl(AlbumAudioDAO dao) {
        super(dao);
    }
}
