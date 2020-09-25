package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumVideoDAO;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumVideoService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumVideoServiceImpl extends GenericServiceAbstract<AlbumVideo, Long> implements AlbumVideoService {

    AlbumVideoDAO albumVideoDAO;

    @Autowired
    public AlbumVideoServiceImpl(AlbumVideoDAO dao) {
        super(dao);
        this.albumVideoDAO = dao;
    }

    @Override
    public AlbumVideo createAlbumVideosWithOwner(AlbumVideo albumVideo) {
        return albumVideoDAO.createAlbumVideoWithOwner(albumVideo);
    }
}
