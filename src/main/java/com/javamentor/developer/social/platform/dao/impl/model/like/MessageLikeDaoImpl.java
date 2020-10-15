package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.MessageLikeDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.like.MessageLike;
import org.springframework.stereotype.Repository;

@Repository
public class MessageLikeDaoImpl extends GenericDaoAbstract<MessageLike, Long> implements MessageLikeDao {
}
