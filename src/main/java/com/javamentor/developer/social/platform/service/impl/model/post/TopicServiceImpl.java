package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.post.Topic;
import com.javamentor.developer.social.platform.service.abstracts.model.post.TopicService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl extends GenericServiceAbstract<Topic, Long> implements TopicService {

    public TopicServiceImpl(GenericDao<Topic, Long> dao) {
        super(dao);
    }
}
