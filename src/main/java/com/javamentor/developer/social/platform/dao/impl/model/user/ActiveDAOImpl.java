package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.ActiveDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ActiveDAOImpl extends GenericDaoAbstract<Active, Long> implements ActiveDAO {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Active getByActiveName(String name) {
               return  entityManager.createQuery(
                "SELECT a from Active a " +
                        "WHERE a.name = :paramName", Active.class)
                       .setParameter("paramName", name.toUpperCase())
                .getSingleResult();
    }
}
