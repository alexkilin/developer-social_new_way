package com.javamentor.developer.social.platform.dao.abstracts.model.token;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.token.VerificationToken;

import java.util.Optional;

public interface VerificationTokenDao extends GenericDao<VerificationToken, Long> {

    Optional<VerificationToken> getByValue(String tokenValue);
}
