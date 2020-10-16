package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.ActiveDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class ActiveDaoImpl extends GenericDaoAbstract<Active, Long> implements ActiveDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<Active> getByActiveName(String name) {
        TypedQuery<Active> query = entityManager.createQuery("SELECT a from Active a " +
                "WHERE a.name = :paramName", Active.class)
                .setParameter("paramName", name.toUpperCase());
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
