package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumAudioDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.UserHasAlbum;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AlbumAudioDAOImpl extends GenericDaoAbstract<AlbumAudios, Long> implements AlbumAudioDAO {

    @PersistenceContext
    EntityManager entityManager;

    AlbumDAO albumDAO;

    @Autowired
    public AlbumAudioDAOImpl(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    @Override
    @Transactional
    public AlbumAudios createAlbumAudiosWithOwner(AlbumAudios albumAudios) {
        create(albumAudios);
        albumDAO.assignAlbumToUser(albumAudios.getAlbum());
        return albumAudios;
    }
}
