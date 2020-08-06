package com.javamentor.developer.social.platform.service.abstracts.model;

import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;
import java.util.List;

public interface UserService extends GenericService<User, Long> {

    List<User> getAll();

}
