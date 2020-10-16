package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.RoleDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class RoleDaoImpl extends GenericDaoAbstract<Role, Long> implements RoleDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<Role> getByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r " +
                        "WHERE r.name = :paramName", Role.class)
                .setParameter("paramName", name.toUpperCase());
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public Optional<Role> getByUserId(Long userId) {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r, User u " +
                        "WHERE u.userId =: userId AND u.role.id = r.id", Role.class)
                .setParameter("userId", userId);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
