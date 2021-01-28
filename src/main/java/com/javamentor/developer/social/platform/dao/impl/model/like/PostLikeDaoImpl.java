package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.PostLikeDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.like.Like;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class PostLikeDaoImpl extends GenericDaoAbstract<PostLike, Long> implements PostLikeDao {

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

    @Override
    public void create(PostLike entity) {
        // Like entity in PostLike is now in detached state, must be in managed state to
        // successfully persist PostLike cause of @MapsId annotation in PostLike
        Like like = entityManager.find(Like.class, entity.getLike().getId());
        entity.setLike(like);

        super.create(entity);
    }

    @Override
    public void delete(PostLike entity) {
        super.delete(entityManager.find(PostLike.class, entity.getId()));
    }
}
