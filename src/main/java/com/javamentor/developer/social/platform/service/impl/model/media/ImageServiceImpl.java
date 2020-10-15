package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.ImageDao;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl extends GenericServiceAbstract<Image, Long> implements ImageService {

    ImageDao imageDAO;

    @Autowired
    public ImageServiceImpl(ImageDao dao) {
        super(dao);
        this.imageDAO = dao;
    }

    @Override
    public Optional<Image> getOptionalById(Long id) {
        return Optional.ofNullable(super.getById(id));
    }
}
