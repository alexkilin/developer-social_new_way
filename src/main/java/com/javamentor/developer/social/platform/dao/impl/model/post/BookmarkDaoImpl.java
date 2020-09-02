package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.BookmarkDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import org.springframework.stereotype.Repository;

@Repository
public class BookmarkDaoImpl extends GenericDaoAbstract<Bookmark, Long> implements BookmarkDao {
}
