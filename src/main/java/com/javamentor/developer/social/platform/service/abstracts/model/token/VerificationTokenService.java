package com.javamentor.developer.social.platform.service.abstracts.model.token;

import com.javamentor.developer.social.platform.models.entity.token.VerificationToken;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface VerificationTokenService extends GenericService<VerificationToken, Long> {

    Optional<VerificationToken> getByValue(String tokenValue);
}
