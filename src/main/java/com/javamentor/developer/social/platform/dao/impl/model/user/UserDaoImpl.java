package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends GenericDaoAbstract<User, Long> implements UserDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return entityManager.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public User getByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email).getSingleResult();
    }

    @Override
    public boolean existByEmail(String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) " +
                        "FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return (count > 0);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u JOIN FETCH u.role JOIN FETCH u.active WHERE u.userId = :id", User.class)
                .setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }


}