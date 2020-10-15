package com.javamentor.developer.social.platform.dao.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupCategoryDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import org.springframework.stereotype.Repository;

@Repository
public class GroupCategoryDaoImpl extends GenericDaoAbstract<GroupCategory, Long> implements GroupCategoryDao {
}
