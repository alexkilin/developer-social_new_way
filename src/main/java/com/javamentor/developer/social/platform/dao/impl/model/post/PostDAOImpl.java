package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.PostDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PostDAOImpl extends GenericDaoAbstract<Post, Long> implements PostDAO {

    private final EntityManager entityManager;

    @Autowired
    public PostDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void deleteByPostId(Long id) {

        entityManager.createNativeQuery("delete from user_tabs as ut where ut.post_id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}