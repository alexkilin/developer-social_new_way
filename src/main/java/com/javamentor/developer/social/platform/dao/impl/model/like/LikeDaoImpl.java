package com.javamentor.developer.social.platform.dao.impl.model.like;

import com.javamentor.developer.social.platform.dao.abstracts.model.like.LikeDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.like.Like;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LikeDaoImpl extends GenericDaoAbstract<Like, Long> implements LikeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Like createLike(Like like) {
        return entityManager.merge(like);
    }
}
