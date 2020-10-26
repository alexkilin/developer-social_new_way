package com.javamentor.developer.social.platform.service.abstracts.model.group;

import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface GroupService extends GenericService<Group, Long> {
    void updateInfo(Group group);
}
