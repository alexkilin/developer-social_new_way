package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.post.Repost;
import com.javamentor.developer.social.platform.service.abstracts.model.post.RepostService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class RepostServiceImpl extends GenericServiceAbstract<Repost, Long> implements RepostService {

    public RepostServiceImpl(GenericDao<Repost, Long> dao) {
        super(dao);
    }
}
