package com.javamentor.developer.social.platform.service.abstracts.model.album;

import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface AlbumAudioService extends GenericService<AlbumAudios, Long> {

    AlbumAudios createAlbumAudiosWithOwner(AlbumAudios albumAudios);

    Optional<AlbumAudios> getByIdWithAudios(Long id);
}
