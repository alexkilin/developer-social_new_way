package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumVideoDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AlbumVideoDAOImpl extends GenericDaoAbstract<AlbumVideo, Long> implements AlbumVideoDAO {

    AlbumDAO albumDAO;

    @Autowired
    public AlbumVideoDAOImpl(AlbumDAO albumDAO){
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
