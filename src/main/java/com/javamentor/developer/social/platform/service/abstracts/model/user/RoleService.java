package com.javamentor.developer.social.platform.service.abstracts.model.user;

import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface RoleService extends GenericService<Role, Long> {
    Optional<Role> getByRoleName(String role);

    Optional<Role> getByUserId(Long userId);
}
