package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.VideosDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import org.springframework.stereotype.Repository;

@Repository
public class VideosDAOImpl extends GenericDaoAbstract<Videos, Long> implements VideosDAO {
}
