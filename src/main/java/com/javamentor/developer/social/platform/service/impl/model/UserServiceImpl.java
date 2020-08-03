package com.javamentor.developer.social.platform.service.impl.model;

import com.javamentor.developer.social.platform.dao.abstracts.model.UserDao;
import com.javamentor.developer.social.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDAO;

    @Autowired
    public UserServiceImpl(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void persist(User user) {
        userDAO.persist(user);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public boolean existsById(Long id) {
        return userDAO.existsById(id);
    }

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }
}
