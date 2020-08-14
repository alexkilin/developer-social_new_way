package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.ImageDAO;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import com.javamentor.developer.social.platform.service.abstracts.model.media.ImageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl extends GenericServiceAbstract<Image, Long> implements ImageService {
    private final ImageDAO DAO;

    @Autowired
    public ImageServiceImpl(ImageDAO dao, ImageDAO dao1) {
        super(dao);
        DAO = dao1;
    }

    public List<Image> getAllByUserId(Long id){
        return DAO.getAllByUserId(id);

    }
}
