package com.javamentor.developer.social.platform.dao.impl;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

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
    public List<T> getPartAudio(int currentPage, int itemsOnPage) {
        return entityManager.createQuery("SELECT a FROM " + clazz.getSimpleName() + " as a").setFirstResult(currentPage)
                .setMaxResults(itemsOnPage).getResultList();
    }


    @Override
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
        T entity = getById(id);
        entityManager.remove(entity);
    }

    @Override
    public T getById(PK id) {
        return (T) entityManager.find(clazz, id);
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
