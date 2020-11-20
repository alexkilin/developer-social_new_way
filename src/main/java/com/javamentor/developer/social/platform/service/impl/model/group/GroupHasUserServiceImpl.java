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
    public final GroupHasUserDao groupHasUserDAO;

    @Autowired
    public GroupHasUserServiceImpl(GroupHasUserDao dao, GroupHasUserDao groupHasUserDAO) {
        super(dao);
        this.groupHasUserDAO = groupHasUserDAO;
    }

    @Transactional
    @Override
    public void setUserIntoGroup(User user, Group group) {

        GroupHasUser groupHasUser = GroupHasUser.builder()
                .user(user)
                .group(group)
                .build();
        groupHasUserDAO.create(groupHasUser);
    }

    @Override
    public boolean verificationUserInGroup(Long groupId, Long userId) {
        return groupHasUserDAO.verificationUserInGroup(groupId, userId);
    }

    @Transactional
    @Override
    public void deleteUserById(Long groupId, Long userId) {
        groupHasUserDAO.deleteUserById(groupId, userId);
    }
}
