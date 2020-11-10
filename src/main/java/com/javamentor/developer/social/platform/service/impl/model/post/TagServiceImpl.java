package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.TagDao;
import com.javamentor.developer.social.platform.models.entity.post.Tag;
import com.javamentor.developer.social.platform.service.abstracts.model.post.TagService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl extends GenericServiceAbstract<Tag, Long> implements TagService {

    private final TagDao dao;

    @Autowired
    public TagServiceImpl(TagDao dao) {
        super(dao);
        this.dao = dao;
    }

    @Override
    public Optional<Tag> getTagByText(String text) {
        return this.dao.getTagByText(text);
    }

    @Override
    @Transactional
    public List<Tag> getTagsByText(List<String> texts) {
        return dao.getTagsByText(texts);
    }
}
