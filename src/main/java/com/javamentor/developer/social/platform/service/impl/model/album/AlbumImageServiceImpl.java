package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumAudioDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDAO;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumAudioService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumImageServiceImpl extends GenericServiceAbstract<AlbumImage, Long> implements AlbumImageService {

    @Autowired
    public AlbumImageServiceImpl(AlbumImageDAO dao) {
        super(dao);
    }
}
