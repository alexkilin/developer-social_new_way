package com.javamentor.developer.social.platform.dao.abstracts.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.user.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {

    Optional<User> getByEmail(String email);

    Optional<User> getByEmailWithRole(String email);

    boolean existByEmail(String email);

    boolean existsAnotherByEmail(String email, Long userId);

    void updateUserPassword(User user);

    void updateInfo(User user);

    Optional<User> getByIdWithAudios(Long id);

    Optional<User> getByIdWithVideos(Long id);
}