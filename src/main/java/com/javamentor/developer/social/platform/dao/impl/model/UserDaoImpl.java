package com.javamentor.developer.social.platform.dao.impl.model;


import com.javamentor.developer.social.platform.dao.abstracts.model.UserDao;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Transactional
    public void create(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void delete(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    public User getById(Long id) {
        return entityManager.find(User.class, id) != null;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    public void deleteById(Long id) {
        entityManager.createQuery("delete from User u where u.userId = " + id);
    }

    @Override
    public boolean existById(Long id) {
        return entityManager.find(User.class, id) != null;
    }
}