package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.media.AlbumDAO;
import com.javamentor.developer.social.platform.models.entity.media.Album;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AlbumService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl extends GenericServiceAbstract<Album, Long> implements AlbumService {

    @Autowired
    public AlbumServiceImpl(AlbumDAO dao) {
        super(dao);
    }
}
