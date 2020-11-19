package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.MediaDao;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import com.javamentor.developer.social.platform.service.abstracts.model.media.MediaService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl extends GenericServiceAbstract<Media, Long> implements MediaService {

    public MediaServiceImpl(MediaDao dao) {
        super(dao);
    }
}
