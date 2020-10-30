package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserFriendsDao;
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
    @SuppressWarnings("unchecked")
    public List<Friend> getUserFriendsById(Long id) {
        return entityManager.createQuery("select f.id, f.user, f.friend " +
                "from Friend f " +
                "where f.user.userId =:paramId")
                .setParameter("paramId", id)
                .getResultList();
    }
}
