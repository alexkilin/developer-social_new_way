package com.javamentor.developer.social.platform.dao.abstracts.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.user.Role;

import java.util.Optional;

public interface RoleDAO extends GenericDao<Role, Long> {
    Optional<Role> getByName(String name);

    Optional<Role> getByUserId(Long userId);
}
