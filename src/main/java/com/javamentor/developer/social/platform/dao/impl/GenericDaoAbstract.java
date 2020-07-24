package com.javamentor.developer.social.platform.dao.impl;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public abstract class GenericDaoAbstract<T, PK extends Serializable> implements GenericDao<T, PK> {
    private Class<T> clazz;

    @PersistenceContext
    private EntityManager entityManager;

    Session session = entityManager.unwrap(Session.class);

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        session.update(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(PK id) {
        entityManager.remove(id);
    }

    @Override
    public T getById(PK id) {
        return (T) entityManager.find(clazz, id);
    }

    @Override
    public List<T> getUserFriendsById(PK id) {
        return new ArrayList<>();
    }
}
