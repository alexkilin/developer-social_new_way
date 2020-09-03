package com.javamentor.developer.social.platform.service.abstracts.model.album;

import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface AlbumService extends GenericService<Album, Long> {
    boolean existsByNameAndMediaType(String name, MediaType type);
}
