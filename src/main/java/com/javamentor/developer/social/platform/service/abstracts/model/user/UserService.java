package com.javamentor.developer.social.platform.service.abstracts.model.user;

import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.List;
import java.util.Optional;

public interface UserService extends GenericService<User, Long> {

    List<User> getAll();

    Optional<User> getByEmail(String email);

    Optional<User> getByEmailWithRole(String email);

    Optional<User> getByIdWithAudios(Long id);

    Optional<User> getByIdWithVideos(Long id);

    boolean existByEmail(String email);

    void updateUserPassword(User user);

    void updateInfo(User user);

    boolean existsAnotherByEmail(String email, Long userId);

    Optional<User> getByEmailEagerlyForDtoConversion(String email);
}
