package com.javamentor.developer.social.platform.dao.abstracts.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.user.User;


public interface AlbumAudioDao extends GenericDao<AlbumAudios, Long> {

    AlbumAudios createAlbumAudiosWithOwner(AlbumAudios albumAudios);

}
