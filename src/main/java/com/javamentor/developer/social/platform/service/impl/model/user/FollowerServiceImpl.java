package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.user.FollowerDAO;
import com.javamentor.developer.social.platform.models.entity.user.Follower;
import com.javamentor.developer.social.platform.service.abstracts.model.user.FollowerService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowerServiceImpl extends GenericServiceAbstract<Follower, Long> implements FollowerService {

    @Autowired
    public FollowerServiceImpl(FollowerDAO dao) {
        super(dao);
    }
}
