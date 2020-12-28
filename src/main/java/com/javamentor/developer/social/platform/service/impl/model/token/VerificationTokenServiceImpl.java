package com.javamentor.developer.social.platform.service.impl.model.token;

import com.javamentor.developer.social.platform.dao.abstracts.model.token.VerificationTokenDao;
import com.javamentor.developer.social.platform.models.entity.token.VerificationToken;
import com.javamentor.developer.social.platform.service.abstracts.model.token.VerificationTokenService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VerificationTokenServiceImpl extends GenericServiceAbstract<VerificationToken, Long> implements VerificationTokenService {

    private final VerificationTokenDao verificationTokenDao;

    public VerificationTokenServiceImpl(VerificationTokenDao verificationTokenDao) {
        super(verificationTokenDao);
        this.verificationTokenDao = verificationTokenDao;
    }

    @Override
    @Transactional
    public Optional<VerificationToken> getByValue(String tokenValue) {
        return verificationTokenDao.getByValue(tokenValue);
    }
}
