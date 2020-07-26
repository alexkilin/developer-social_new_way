package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.media.VideosDAO;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import com.javamentor.developer.social.platform.service.abstracts.model.media.VideosService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideosServiceImpl extends GenericServiceAbstract<Videos, Long> implements VideosService {

    @Autowired
    public VideosServiceImpl(VideosDAO dao) {
        super(dao);
    }
}
