package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.PostLikeDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.like.PostLike;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class PostLikeDaoImpl extends GenericDaoAbstract<PostLike, Long> implements PostLikeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<PostLike> getPostLikeByPostIdAndUserId(Long postId, Long userId) {
        return (List<PostLike>) entityManager.createQuery(
                "SELECT pl, l, p FROM PostLike pl JOIN pl.like l LEFT JOIN pl.post p " +
                        "WHERE pl.post.id = :postId AND pl.like.user.userId = :userId")
                .setParameter("postId", postId)
                .setParameter("userId", userId)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        if (objects[0] != null) {
                            return objects[0];
                        }
                        return null;
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getResultList();
    }
}
