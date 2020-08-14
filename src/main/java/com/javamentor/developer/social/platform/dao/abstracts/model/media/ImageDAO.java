package com.javamentor.developer.social.platform.dao.abstracts.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.media.Image;

import java.util.List;

public interface ImageDAO extends GenericDao<Image, Long> {
    List<Image> getAllByUserId(Long id);
}
