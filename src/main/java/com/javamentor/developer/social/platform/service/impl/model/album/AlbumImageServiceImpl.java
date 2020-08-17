package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDAO;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumImageServiceImpl extends GenericServiceAbstract<AlbumImage, Long> implements AlbumImageService {
    private final AlbumImageDAO DAO;

    @Autowired
    public AlbumImageServiceImpl(AlbumImageDAO dao) {
        super(dao);
        DAO = dao;
    }

    @Override
    public List<AlbumImage> getAllByUserId(Long id) {
        return DAO.getAllByUserId(id);
    }

}
