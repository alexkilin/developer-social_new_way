package com.javamentor.developer.social.platform.service.abstracts.model.user;

import com.javamentor.developer.social.platform.models.dto.users.UserResetPasswordDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.List;
import java.util.Optional;

public interface UserService extends GenericService<User, Long> {

    List<User> getAll();

    Optional<User> getByEmail(String email);

    boolean existByEmail(String email);

    void setPassword(UserResetPasswordDto userResetPasswordDto, Long userId);

    void updateInfo(User user);

    boolean existsAnotherByEmail(String email, Long userId);

    User getPrincipal();

}
