package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.RoleDAO;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.service.abstracts.model.user.RoleService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends GenericServiceAbstract<Role, Long> implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO dao) {
        super(dao);
        roleDAO = dao;
    }

    @Override
    public Role getByRoleName(String name) {
        return roleDAO.getByName(name);
    }

    @Override
    public Role getByUserId(Long userId) {
        return roleDAO.getByUserId(userId);
    }
}
