package com.javamentor.developer.social.platform.service.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupCategoryDAO;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupCategoryService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupCategoryServiceImpl extends GenericServiceAbstract<GroupCategory, Long> implements GroupCategoryService {

    @Autowired
    public GroupCategoryServiceImpl(GroupCategoryDAO dao) {
        super(dao);
    }
}
