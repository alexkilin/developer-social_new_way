package com.javamentor.developer.social.platform.dao.impl;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public abstract class GenericDaoAbstract<T, PK extends Serializable> implements GenericDao<T, PK> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public GenericDaoAbstract() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clazz = (Class) pt.getActualTypeArguments()[0];
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return entityManager.createQuery("SELECT a FROM " + clazz.getSimpleName() + " as a").getResultList();
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
        T entity = getById(id).get();
        entityManager.remove(entity);
    }

    @Override
    public Optional<T> getById(PK id) {
        TypedQuery<T> query = entityManager.createQuery(
                "SELECT a FROM " + clazz.getSimpleName()+ " as a" + " WHERE a.id = :paramId", clazz)
                .setParameter("paramId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public boolean existById(PK id) {
            Long count = entityManager.createQuery(
                    "SELECT count(b) " +
                            "FROM " + clazz.getSimpleName() + " b " +
                            "WHERE b.id = :paramId", Long.class)
                    .setParameter("paramId", id)
                    .getSingleResult();
            return (count > 0);
    }
}
