package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

public abstract class GenericServiceAbstract<T, PK extends Serializable, R extends GenericDaoAbstract<T, PK>> implements GenericService<T, PK> {
    private final R dao;

    @Autowired
    public GenericServiceAbstract(R dao) {
        this.dao = dao;
    }

    @Transactional
    @Override
    public void create(T entity) {
        dao.create(entity);
    }

    @Transactional
    @Override
    public void update(T entity) {
        dao.update(entity);
    }

    @Transactional
    @Override
    public T getById(PK id) {
        return dao.getById(id);
    }

    @Transactional
    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Transactional
    @Override
    public void deleteById(PK id) {
        dao.deleteById(id);
    }

    @Transactional
    @Override
    public boolean existById(PK id) {
        return dao.existById(id);
    }
}
