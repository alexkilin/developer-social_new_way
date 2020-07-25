package com.javamentor.developer.social.platform.dao.impl;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericDaoAbstract<T, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> clazz;

    public GenericDaoAbstract() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clazz = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
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
    public boolean existById(PK id) {
        return getById(id) != null;
    }
}
