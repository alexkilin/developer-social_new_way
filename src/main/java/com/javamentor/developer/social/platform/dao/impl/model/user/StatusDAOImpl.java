package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.StatusDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Status;
import org.springframework.stereotype.Repository;

@Repository
public class StatusDAOImpl extends GenericDaoAbstract<Status, Long> implements StatusDAO {
}
