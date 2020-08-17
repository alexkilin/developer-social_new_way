package com.javamentor.developer.social.platform.service.abstracts.model.album;


import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.List;

public interface AlbumImageService extends GenericService<AlbumImage, Long> {

    List<AlbumImage> getAllByUserId(Long id);

}
