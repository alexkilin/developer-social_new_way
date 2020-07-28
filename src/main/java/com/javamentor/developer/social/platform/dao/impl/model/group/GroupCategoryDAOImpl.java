package com.javamentor.developer.social.platform.dao.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupCategoryDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import org.springframework.stereotype.Repository;

@Repository
public class GroupCategoryDAOImpl extends GenericDaoAbstract<GroupCategory, Long> implements GroupCategoryDAO {
}
