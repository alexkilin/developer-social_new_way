package com.javamentor.developer.social.platform.service.abstracts.model.group;

import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.group.GroupHasUser;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface GroupHasUserService extends GenericService<GroupHasUser, Long> {

    void setUserIntoGroup(User user, Group group);

    boolean verificationUserInGroup(Long groupId, Long userId);

    void deleteUserById(Long groupId, Long userId);
}
