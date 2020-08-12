package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumAudioDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumAudioDAOImpl extends GenericDaoAbstract<AlbumAudios, Long> implements AlbumAudioDAO {
}
