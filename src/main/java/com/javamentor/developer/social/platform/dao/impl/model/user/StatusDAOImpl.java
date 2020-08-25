package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.StatusDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Status;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StatusDAOImpl extends GenericDaoAbstract<Status, Long> implements StatusDAO {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Status getByName(String name) {
        return (Status) entityManager.createQuery(
                  "FROM Status s" +
                     "WHERE s.name = :paramName").setParameter("paramName", name)
                     .getSingleResult();
    }
}
