package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.TagDao;
import com.javamentor.developer.social.platform.models.entity.post.Tag;
import com.javamentor.developer.social.platform.service.abstracts.model.post.TagService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl extends GenericServiceAbstract<Tag, Long> implements TagService {

    @Autowired
    public TagServiceImpl(TagDao dao) {
        super(dao);
    }
}
