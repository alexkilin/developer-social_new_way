package com.javamentor.developer.social.platform.dao.abstracts.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;


public interface AlbumAudioDao extends GenericDao<AlbumAudios, Long> {

    AlbumAudios createAlbumAudiosWithOwner(AlbumAudios albumAudios);

}
