package com.javamentor.developer.social.platform.service.abstracts.model.media;

import com.javamentor.developer.social.platform.models.entity.media.Videos;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface VideosService extends GenericService<Videos, Long> {

    public boolean addVideoInCollectionsOfUser(User user, Long videoId);
}
