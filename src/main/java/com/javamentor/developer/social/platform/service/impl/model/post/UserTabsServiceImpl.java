package com.javamentor.developer.social.platform.service.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.post.UserTabsDao;
import com.javamentor.developer.social.platform.models.entity.post.Post;
import com.javamentor.developer.social.platform.models.entity.post.UserTabs;
import com.javamentor.developer.social.platform.service.abstracts.model.post.UserTabsService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTabsServiceImpl extends GenericServiceAbstract<UserTabs, Long> implements UserTabsService {

    private final UserTabsDao userTabsDao;

    @Autowired
    public UserTabsServiceImpl(GenericDao<UserTabs, Long> dao, UserTabsDao userTabsDao) {
        super(dao);
        this.userTabsDao = userTabsDao;
    }

    @Override
    @Transactional
    public void deletePost(Post post) {
        userTabsDao.deletePost(post);
    }
}
