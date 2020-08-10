package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumAudioDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumImageDAOImpl extends GenericDaoAbstract<AlbumImage, Long> implements AlbumImageDAO {
}
