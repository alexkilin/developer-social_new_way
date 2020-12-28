package com.javamentor.developer.social.platform.dao.impl.model.token;

import com.javamentor.developer.social.platform.dao.abstracts.model.token.VerificationTokenDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.token.VerificationToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class VerificationTokenDaoImpl extends GenericDaoAbstract<VerificationToken, Long> implements VerificationTokenDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<VerificationToken> getByValue(String tokenValue) {
        TypedQuery<VerificationToken> query = entityManager.createQuery("SELECT v " +
                "FROM VerificationToken AS v " +
                "JOIN FETCH v.user " +
                "WHERE v.value = :tokenValue", VerificationToken.class)
                .setParameter("tokenValue", tokenValue);

        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
