package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.PostDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import org.springframework.stereotype.Repository;

@Repository
public class PostDAOImpl extends GenericDaoAbstract<Post, Long> implements PostDAO {
}
