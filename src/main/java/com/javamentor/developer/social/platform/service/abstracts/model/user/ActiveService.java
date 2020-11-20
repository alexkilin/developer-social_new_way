package com.javamentor.developer.social.platform.service.abstracts.model.user;

import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface ActiveService extends GenericService<Active, Long> {

    Optional<Active> getByActiveName(String active);
}
