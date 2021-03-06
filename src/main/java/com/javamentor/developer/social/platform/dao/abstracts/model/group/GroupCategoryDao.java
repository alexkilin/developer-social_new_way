package com.javamentor.developer.social.platform.dao.abstracts.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;

import java.util.List;
import java.util.Optional;

public interface GroupCategoryDao extends GenericDao<GroupCategory, Long> {

    Optional <GroupCategory> getGroupCategoryByName( String category);
    
}
