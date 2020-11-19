package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.post.BookmarkDao;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import com.javamentor.developer.social.platform.service.abstracts.model.post.BookmarkService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookmarkServiceImpl extends GenericServiceAbstract<Bookmark, Long> implements BookmarkService {

    private final BookmarkDao bookmarkDao;

    @Autowired
    public BookmarkServiceImpl(GenericDao<Bookmark, Long> dao, BookmarkDao bookmarkDao) {
        super(dao);
        this.bookmarkDao = bookmarkDao;
    }

    @Override
    @Transactional
    public void deleteBookmarkByPostIdAndUserId(Long postId, Long userId) {
        bookmarkDao.deleteBookmarkByPostIdAndUserId(postId, userId);
    }
}