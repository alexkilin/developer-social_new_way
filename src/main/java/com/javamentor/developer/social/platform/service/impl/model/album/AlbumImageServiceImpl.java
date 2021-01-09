package com.javamentor.developer.social.platform.service.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDao;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.service.abstracts.model.album.AlbumImageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AlbumImageServiceImpl extends GenericServiceAbstract<AlbumImage, Long> implements AlbumImageService {

    private AlbumImageDao albumImageDao;

    public AlbumImageServiceImpl(AlbumImageDao albumImageDao) {
        super(albumImageDao);
        this.albumImageDao = albumImageDao;
    }

    @Override
    @Transactional
    public Optional<AlbumImage> getByIdWithAlbum(Long id) {
        return albumImageDao.getByIdWithAlbum(id);
    }
}
