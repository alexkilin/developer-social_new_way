package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.BookmarkDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.post.Bookmark;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class BookmarkDaoImpl extends GenericDaoAbstract<Bookmark, Long> implements BookmarkDao {

    @Override
    public void deleteBookmarkByPostIdAndUserId(Long postId, Long userId) {
        entityManager.createQuery("DELETE FROM Bookmark b " +
                "WHERE b.post.id = :postId " +
                "AND b.user.userId = :userId")
                .setParameter("postId", postId)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public Optional<Bookmark> getBookmarkByPostIdAndUserId(Long postId, Long userId) {
        TypedQuery<Bookmark > query = entityManager.createQuery(
                "SELECT bm FROM Bookmark bm " +
                        "WHERE  bm.post.id = :postId " +
                        "AND bm.user.userId = :userId", Bookmark.class)
                .setParameter("postId", postId)
                .setParameter("userId", userId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
