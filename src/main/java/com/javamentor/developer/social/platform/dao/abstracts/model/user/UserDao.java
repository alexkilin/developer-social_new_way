package com.javamentor.developer.social.platform.dao.abstracts.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {

    List<User> getAll();

    Optional<User> getByEmail(String email);

    boolean existByEmail(String email);

    boolean existsAnotherByEmail(String email, Long userId);

    void updateInfo(User user);
}