package com.javamentor.developer.social.platform.service.abstracts.model.album;



import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface AlbumAudioService extends GenericService<AlbumAudios, Long> {
    AlbumAudios createAlbumAudiosWithOwner(AlbumAudios albumAudios);
}
