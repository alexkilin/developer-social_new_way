package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.ImageDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDAOImpl extends GenericDaoAbstract<Image, Long> implements ImageDAO {
}
