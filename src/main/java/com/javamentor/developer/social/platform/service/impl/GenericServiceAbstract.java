package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class GenericServiceAbstract<T, PK extends Serializable, R extends GenericDaoAbstract<T, PK>> implements GenericService<T, PK> {
    private final R dao;

    @Autowired
    public GenericServiceAbstract(R dao) {
        this.dao = dao;
    }

    @Override
    public void create(T entity) {
        dao.create(entity);
    }

    @Override
    public void update(T entity) {
        dao.update(entity);
    }

    @Override
    public T getById(PK id) {
        return dao.getById(id);
    }

    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    public void deleteById(PK id) {
        dao.deleteById(id);
    }
}
