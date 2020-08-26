package com.javamentor.developer.social.platform.service.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupHasUserDAO;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.group.GroupHasUser;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupHasUserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class GroupHasUserServiceImpl extends GenericServiceAbstract<GroupHasUser, Long> implements GroupHasUserService {
    public final GroupHasUserDAO groupHasUserDAO;

    @Autowired
    public GroupHasUserServiceImpl(GroupHasUserDAO dao, GroupHasUserDAO groupHasUserDAO) {
        super(dao);
        this.groupHasUserDAO = groupHasUserDAO;
    }

    @Transactional
    @Override
    public void setUserIntoGroup(User user, Group group) {

        GroupHasUser groupHasUser = GroupHasUser.builder()
                .user(user)
                .group(group)
                .persistDate(LocalDateTime.now())
                .build();
        groupHasUserDAO.create(groupHasUser);
    }

    @Override
    public boolean verificationUserInGroup(Long groupId, Long userId) {
        return groupHasUserDAO.verificationUserInGroup(groupId, userId);
    }
}
