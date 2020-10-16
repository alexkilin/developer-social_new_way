package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDao;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl extends GenericServiceAbstract<Album, Long> implements AlbumService {

    AlbumDao albumDAO;

    @Autowired
    public AlbumServiceImpl(AlbumDao dao) {
        super(dao);
        this.albumDAO = dao;
    }

    public boolean existsByNameAndMediaType(String name, MediaType type) {
        return albumDAO.existsByNameAndMediaType(name, type);
    }
}
