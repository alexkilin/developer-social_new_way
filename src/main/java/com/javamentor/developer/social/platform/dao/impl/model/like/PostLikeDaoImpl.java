package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.PostLikeDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class PostLikeDaoImpl extends GenericDaoAbstract<PostLike, Long> implements PostLikeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<PostLike> getPostLikeByPostIdAndUserId(Long postId, Long userId) {
        TypedQuery<PostLike> query = entityManager.createQuery(
                "SELECT pl FROM PostLike pl JOIN FETCH pl.like " +
                        "WHERE  pl.post.id = :postId " +
                        "AND pl.like.user.userId = :userId", PostLike.class)
                .setParameter("postId", postId)
                .setParameter("userId", userId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
