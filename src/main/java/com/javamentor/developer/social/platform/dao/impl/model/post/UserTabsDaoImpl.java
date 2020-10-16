package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.UserTabsDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.UserTabs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class UserTabsDaoImpl extends GenericDaoAbstract<UserTabs, Long> implements UserTabsDao {

    private final EntityManager entityManager;

    @Autowired
    public UserTabsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void deletePost(Post post) {
        entityManager.createNativeQuery("delete from bookmarks where post_id = :id")
                .setParameter("id", post.getId())
                .executeUpdate();

        entityManager.createNativeQuery("delete from group_wal where post_id = :id")
                .setParameter("id", post.getId())
                .executeUpdate();

        entityManager.createNativeQuery("delete from post_comment where post_id = :id")
                .setParameter("id", post.getId())
                .executeUpdate();

        entityManager.createNativeQuery("delete from post_like where post_id = :id")
                .setParameter("id", post.getId())
                .executeUpdate();

        entityManager.createQuery("delete from UserTabs where post.id = :id")
                .setParameter("id", post.getId())
                .executeUpdate();

        entityManager.remove(post);
    }
}
