package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.VideosDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import org.springframework.stereotype.Repository;

@Repository
public class VideosDaoImpl extends GenericDaoAbstract<Videos, Long> implements VideosDao {
}
