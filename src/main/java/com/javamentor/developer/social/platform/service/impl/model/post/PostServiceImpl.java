package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.post.PostDAO;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.service.abstracts.model.post.PostService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends GenericServiceAbstract<Post, Long> implements PostService {

    @Autowired
    public PostServiceImpl(PostDAO dao) {
        super(dao);
    }
}
