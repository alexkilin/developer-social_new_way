package com.javamentor.developer.social.platform.dao.abstracts.model;

import java.util.List;

public interface UserDao {
    void persist(User user);

    void update(User user);

    void delete(User user);

    boolean existsById(Long id);

    List<User> getAll();

    void deleteById(Long id);
}