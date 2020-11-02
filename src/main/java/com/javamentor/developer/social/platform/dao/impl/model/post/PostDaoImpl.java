package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.PostDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostDaoImpl extends GenericDaoAbstract<Post, Long> implements PostDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void deletePostFromUserWallById(Long id) {

        entityManager.createQuery("DELETE FROM UserTabs WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
