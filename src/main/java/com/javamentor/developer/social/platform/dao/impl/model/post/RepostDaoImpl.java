package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.RepostDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Repost;
import org.springframework.stereotype.Repository;

@Repository
public class RepostDaoImpl extends GenericDaoAbstract<Repost, Long> implements RepostDao {
}
