package com.javamentor.developer.social.platform.dao.abstracts.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.models.entity.user.User;

import java.util.Optional;

public interface ActiveDao extends GenericDao<Active, Long> {
    Optional<Active> getByActiveName(String active);
}
