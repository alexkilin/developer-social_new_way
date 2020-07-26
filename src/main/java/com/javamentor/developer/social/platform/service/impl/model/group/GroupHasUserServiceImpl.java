package com.javamentor.developer.social.platform.service.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupHasUserDAO;
import com.javamentor.developer.social.platform.models.entity.group.GroupHasUser;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupHasUserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupHasUserServiceImpl extends GenericServiceAbstract<GroupHasUser, Long> implements GroupHasUserService {

    @Autowired
    public GroupHasUserServiceImpl(GroupHasUserDAO dao) {
        super(dao);
    }
}
