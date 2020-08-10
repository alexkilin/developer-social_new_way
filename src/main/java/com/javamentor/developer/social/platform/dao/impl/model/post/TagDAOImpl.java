package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.TagDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Tag;
import org.springframework.stereotype.Repository;

@Repository
public class TagDAOImpl extends GenericDaoAbstract<Tag, Long> implements TagDAO {
}
