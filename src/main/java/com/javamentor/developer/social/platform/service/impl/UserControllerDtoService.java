package com.javamentor.developer.social.platform.service.impl;

import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class UserControllerDtoService implements GenericService {

    private final GenericDaoAbstract daoAbstract;

    @Autowired
    public UserControllerDtoService(GenericDaoAbstract daoAbstract) {
        this.daoAbstract = daoAbstract;
    }

    @Override
    public void create(Object entity) {
        daoAbstract.create(entity);
    }

    @Override
    public void update(Object entity) {
        daoAbstract.update(entity);
    }

    @Override
    public Object getById(Serializable id) {
        return daoAbstract.getById(id);
    }

    @Override
    public void delete(Object entity) {
        daoAbstract.delete(entity);
    }

    @Override
    public void deleteById(Serializable id) {
        daoAbstract.deleteById(id);
    }

    @Override
    public List getAllUsers() {
        return daoAbstract.getAllUsers();
    }

    @Override
    public List getUserFriendsById(Serializable id) {
        return daoAbstract.getUserFriendsById(id);
    }
}
