package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.ActiveDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveDAOImpl extends GenericDaoAbstract<Active, Long> implements ActiveDAO {
}
