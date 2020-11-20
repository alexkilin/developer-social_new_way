package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class GenericServiceAbstract<T, PK extends Serializable> implements GenericService<T, PK> {
    private final GenericDao<T, PK> dao;

    public GenericServiceAbstract(GenericDao<T, PK> dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public void create(T entity) {
        dao.create(entity);
    }

    @Override
    @Transactional
    public List<T> getPart(int currentPage, int itemsOnPage) {
        return dao.getPartAudio(currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public List<T> getAll(){
      return dao.getAll();
    }

    @Override
    @Transactional
    public void update(T entity) {
        dao.update(entity);
    }

    @Override
    @Transactional
    public Optional<T> getById(PK id) {
        return dao.getById(id);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(PK id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional
    public boolean existById(PK id) {
        return dao.existById(id);
    }
}
