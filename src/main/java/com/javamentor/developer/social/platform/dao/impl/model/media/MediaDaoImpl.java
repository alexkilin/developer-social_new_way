package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.MediaDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Media;
import org.springframework.stereotype.Repository;

@Repository
public class MediaDaoImpl extends GenericDaoAbstract<Media, Long> implements MediaDao {
}
