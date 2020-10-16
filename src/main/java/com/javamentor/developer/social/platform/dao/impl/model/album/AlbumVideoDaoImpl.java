package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumVideoDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AlbumVideoDaoImpl extends GenericDaoAbstract<AlbumVideo, Long> implements AlbumVideoDao {

    AlbumDao albumDAO;

    @Autowired
    public AlbumVideoDaoImpl(AlbumDao albumDAO){
        this.albumDAO = albumDAO;
    }

    @Override
    @Transactional
    public AlbumVideo createAlbumVideoWithOwner(AlbumVideo albumVideo) {
        create(albumVideo);
        albumDAO.assignAlbumToUser(albumVideo.getAlbum());
        return albumVideo;
    }
}
