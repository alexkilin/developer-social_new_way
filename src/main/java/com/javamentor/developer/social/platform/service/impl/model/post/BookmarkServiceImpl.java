package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import com.javamentor.developer.social.platform.service.abstracts.model.post.BookmarkService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class BookmarkServiceImpl extends GenericServiceAbstract<Bookmark, Long> implements BookmarkService {
    public BookmarkServiceImpl(GenericDao<Bookmark, Long> dao) {
        super(dao);
    }
}