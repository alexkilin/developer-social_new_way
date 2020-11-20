package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumVideoDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumVideoService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlbumVideoServiceImpl extends GenericServiceAbstract<AlbumVideo, Long> implements AlbumVideoService {

    private final AlbumVideoDao albumVideoDao;

    @Autowired
    public AlbumVideoServiceImpl(AlbumVideoDao dao) {
        super(dao);
        this.albumVideoDao = dao;
    }

    @Override
    @Transactional
    public AlbumVideo createAlbumVideosWithOwner(AlbumVideo albumVideo) {
        return albumVideoDao.createAlbumVideoWithOwner(albumVideo);
    }
}
