package com.javamentor.developer.social.platform.dao.abstracts.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;

import java.util.Optional;

public interface AlbumVideoDao extends GenericDao<AlbumVideo, Long> {
    AlbumVideo createAlbumVideoWithOwner(AlbumVideo albumVideo);

    Optional<AlbumVideo> getByIdWithVideos(Long id);
}
