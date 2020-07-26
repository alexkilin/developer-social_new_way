package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.LikeDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.like.Like;
import org.springframework.stereotype.Repository;

@Repository
public class LikeDAOImpl extends GenericDaoAbstract<Like, Long> implements LikeDAO {
}
