package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.TopicDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Topic;
import org.springframework.stereotype.Repository;

@Repository
public class TopicDaoImpl extends GenericDaoAbstract<Topic, Long> implements TopicDao {
}
