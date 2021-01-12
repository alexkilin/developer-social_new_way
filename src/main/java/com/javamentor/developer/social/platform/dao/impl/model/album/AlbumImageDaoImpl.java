package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AlbumImageDaoImpl extends GenericDaoAbstract<AlbumImage, Long> implements AlbumImageDao {

    private final AlbumDao albumDAO;

    @Autowired
    public AlbumImageDaoImpl(AlbumDao albumDAO) {
        this.albumDAO = albumDAO;
    }


    @Override
    @Transactional
    public AlbumImage createAlbumImageWithOwner( AlbumImage albumImage ) {
        create(albumImage);
        albumDAO.assignAlbumToUser(albumImage.getAlbum());
        return albumImage;
    }
}
