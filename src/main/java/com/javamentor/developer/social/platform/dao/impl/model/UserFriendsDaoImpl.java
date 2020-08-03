package com.javamentor.developer.social.platform.dao.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.UserFriendsDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserFriendsDaoImpl extends ReadWriteDaoImpl<Friend, Long> implements UserFriendsDao {

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
