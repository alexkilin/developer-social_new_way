package com.javamentor.developer.social.platform.dao.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupHasUserDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.group.GroupHasUser;
import org.springframework.stereotype.Repository;

@Repository
public class GroupHasUserDAOImpl extends GenericDaoAbstract<GroupHasUser, Long> implements GroupHasUserDAO {
}
