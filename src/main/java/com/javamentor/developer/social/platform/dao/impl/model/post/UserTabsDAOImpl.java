package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.UserTabsDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.post.UserTabs;
import org.springframework.stereotype.Repository;

@Repository
public class UserTabsDAOImpl extends GenericDaoAbstract<UserTabs, Long> implements UserTabsDAO {
}
