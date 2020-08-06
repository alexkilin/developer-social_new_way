package com.javamentor.developer.social.platform.dao.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.UserFriendsDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserFriendsDaoImpl extends GenericDaoAbstract<Friend, Long> implements UserFriendsDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Friend> getUserFriendsById(Long id) {
        return entityManager.createQuery("select * " +
                "from friends f " +
                "join users u " +
                "on f.user_id = u.id" +
                "where u.id = " + id)
                .getResultList();
    }
}
