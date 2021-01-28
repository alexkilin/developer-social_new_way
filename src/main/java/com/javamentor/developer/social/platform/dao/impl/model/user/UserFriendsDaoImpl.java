package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserFriendsDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserFriendsDaoImpl extends GenericDaoAbstract<Friend, Long> implements UserFriendsDao {

    @Override
    @SuppressWarnings("unchecked")
    public List<Friend> getUserFriendsById(Long id) {
        return entityManager.createQuery("SELECT f.id, f.user, f.friend " +
                "FROM Friend f " +
                "WHERE f.user.userId =:paramId " +
                "ORDER BY f.id ASC")
                .setParameter("paramId", id)
                .getResultList();
    }
}
