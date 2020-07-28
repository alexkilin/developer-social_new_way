package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.RoleDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDAOImpl extends GenericDaoAbstract<Role, Long> implements RoleDAO {
}
