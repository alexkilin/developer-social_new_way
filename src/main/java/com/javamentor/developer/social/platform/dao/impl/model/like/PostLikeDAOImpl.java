package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.PostLikeDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import org.springframework.stereotype.Repository;

@Repository
public class PostLikeDAOImpl extends GenericDaoAbstract<PostLike, Long> implements PostLikeDAO {
}
