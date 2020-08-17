package com.javamentor.developer.social.platform.dao.abstracts.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;

import java.util.List;

public interface AlbumImageDAO extends GenericDao<AlbumImage, Long> {

    List<AlbumImage> getAllByUserId(Long id);
}
