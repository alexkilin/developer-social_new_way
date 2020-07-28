package com.javamentor.developer.social.platform.service.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupDAO;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl extends GenericServiceAbstract<Group, Long> implements GroupService {

    @Autowired
    public GroupServiceImpl(GroupDAO dao) {
        super(dao);
    }
}
