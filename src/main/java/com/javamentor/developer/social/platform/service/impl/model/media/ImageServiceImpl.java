package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.media.ImageDAO;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl extends GenericServiceAbstract<Image, Long> implements ImageService {

    ImageDAO imageDAO;

    @Autowired
    public ImageServiceImpl(ImageDAO dao) {
        super(dao);
        this.imageDAO = dao;
    }

    @Override
    public Optional<Image> getOptionalById(Long id) {
        return Optional.ofNullable(super.getById(id));
    }
}
