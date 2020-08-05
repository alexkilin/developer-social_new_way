package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.post.UserTabs;
import com.javamentor.developer.social.platform.service.abstracts.model.post.UserTabsService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class UserTabsServiceImpl extends GenericServiceAbstract<UserTabs, Long> implements UserTabsService {
    public UserTabsServiceImpl(GenericDao<UserTabs, Long> dao) {
        super(dao);
    }
}
