package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.BookmarkDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BookmarkDaoImpl extends GenericDaoAbstract<Bookmark, Long> implements BookmarkDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void deleteBookmarkByPostIdAndUserId(Long postId, Long userId) {
        entityManager.createQuery("DELETE FROM Bookmark b " +
                "WHERE b.post.id = :postId " +
                "AND b.user.userId = :userId")
                .setParameter("postId", postId)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
