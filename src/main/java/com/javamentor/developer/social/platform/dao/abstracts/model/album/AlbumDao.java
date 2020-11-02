package com.javamentor.developer.social.platform.dao.abstracts.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;

public interface AlbumDao extends GenericDao<Album, Long> {
    boolean existsByNameAndMediaType(String name, MediaType type);

    void assignAlbumToUser (Album album);
}
