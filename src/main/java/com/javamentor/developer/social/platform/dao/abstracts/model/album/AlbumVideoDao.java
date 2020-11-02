package com.javamentor.developer.social.platform.dao.abstracts.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;

public interface AlbumVideoDao extends GenericDao<AlbumVideo, Long> {
    AlbumVideo createAlbumVideoWithOwner(AlbumVideo albumVideo);
}
