package com.javamentor.developer.social.platform.service.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupHasUserDao;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.group.GroupHasUser;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupHasUserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupHasUserServiceImpl extends GenericServiceAbstract<GroupHasUser, Long> implements GroupHasUserService {

    private final GroupHasUserDao groupHasUserDao;

    @Autowired
    public GroupHasUserServiceImpl(GroupHasUserDao dao) {
        super(dao);
        this.groupHasUserDao = dao;
    }

    @Override
    @Transactional
    public void setUserIntoGroup(User user, Group group) {
        GroupHasUser groupHasUser = GroupHasUser.builder()
                .user(user)
                .group(group)
                .build();
        groupHasUserDao.create(groupHasUser);
    }

    @Override
    @Transactional
    public boolean verificationUserInGroup(Long groupId, Long userId) {
        return groupHasUserDao.verificationUserInGroup(groupId, userId);
    }

    @Override
    @Transactional
    public void deleteUserById(Long groupId, Long userId) {
        groupHasUserDao.deleteUserById(groupId, userId);
    }
}
