package com.javamentor.developer.social.platform.service.abstracts.model.user;

import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;
import java.util.List;

public interface UserService extends GenericService<User, Long> {

    List<User> getAll();

    User getByEmail(String email);

    boolean existByEmail(String email);
}
