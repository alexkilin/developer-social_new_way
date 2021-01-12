package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.PostDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import org.springframework.stereotype.Repository;

@Repository
public class PostDaoImpl extends GenericDaoAbstract<Post, Long> implements PostDao {

    @Override
    public void deletePostFromUserWallById(Long id) {

        entityManager.createQuery("DELETE FROM UserTabs WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
