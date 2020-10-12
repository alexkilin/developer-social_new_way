package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.TagDAO;
import com.javamentor.developer.social.platform.models.entity.post.Tag;
import com.javamentor.developer.social.platform.service.abstracts.model.post.TagService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl extends GenericServiceAbstract<Tag, Long> implements TagService {

    private final TagDAO tagDao;

    @Autowired
    public TagServiceImpl(TagDAO dao) {
        super(dao);
        this.tagDao = dao;
    }

    @Override
    public Optional<Tag> getTagByText(String text) {
        return tagDao.getByName(text);
    };
}
