package com.javamentor.developer.social.platform.dao.abstracts.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;

import java.util.Optional;

public interface AlbumImageDao extends GenericDao<AlbumImage, Long> {

    Optional<AlbumImage> getByIdWithImages(Long id);
    AlbumImage createAlbumImageWithOwner( AlbumImage albumImage);

}
