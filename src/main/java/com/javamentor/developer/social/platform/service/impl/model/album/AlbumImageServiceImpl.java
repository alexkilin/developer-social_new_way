package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlbumImageServiceImpl extends GenericServiceAbstract<AlbumImage, Long> implements AlbumImageService {

    AlbumImageDao albumImageDAO;

    @Autowired
    public AlbumImageServiceImpl(AlbumImageDao dao) {
        super(dao);
        this.albumImageDAO = dao;
    }

}
