package com.javamentor.developer.social.platform.service.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.ReadWriteDAO;
import com.javamentor.developer.social.platform.service.abstracts.model.ReadWriteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class ReadWriteServiceImpl<T, PK> implements ReadWriteService<T, PK> {


    private final ReadWriteDAO<T, PK> readWriteDao;

    protected ReadWriteServiceImpl(ReadWriteDAO<T, PK> readWriteDao) {
        this.readWriteDao = readWriteDao;
    }

    @Override
    @Transactional
    public void persist(T t) {
        readWriteDao.persist(t);
    }

    @Override
    @Transactional
    public void update(T t) {
        readWriteDao.update(t);
    }

    @Override
    @Transactional
    public void delete(T t) {
        readWriteDao.delete(t);
    }

    @Override
    public boolean existsById(PK id) {
        return readWriteDao.existsById(id);
    }

    @Override
    public List<T> getAll() {
        return readWriteDao.getAll();
    }

    @Override
    public void deleteById(PK id) {
        readWriteDao.deleteById(id);
    }

    @Override
    public List<T> getUserFriendsById(PK id) {
        return readWriteDao.getUserFriendsById(id);
    }
}
