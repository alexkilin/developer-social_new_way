package com.javamentor.developer.social.platform.service.abstracts.model.media;

import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface AudiosService extends GenericService<Audios, Long> {

    boolean addAudioInCollectionsOfUser(User user, Long audioId);
}
