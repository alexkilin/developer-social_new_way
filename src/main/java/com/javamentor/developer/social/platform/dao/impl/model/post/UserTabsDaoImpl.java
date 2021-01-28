package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.UserTabsDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.UserTabs;
import org.springframework.stereotype.Repository;

@Repository
public class UserTabsDaoImpl extends GenericDaoAbstract<UserTabs, Long> implements UserTabsDao {

    @Override
    public void deletePost(Post post) {
        // Post is in detached state, must be in managed
        Post managedPost = entityManager.find(Post.class, post.getId());
        entityManager.remove(managedPost);
    }
}
