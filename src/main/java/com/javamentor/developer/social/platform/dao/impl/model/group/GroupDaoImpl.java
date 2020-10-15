package com.javamentor.developer.social.platform.dao.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDaoImpl extends GenericDaoAbstract<Group, Long> implements GroupDao {
}
