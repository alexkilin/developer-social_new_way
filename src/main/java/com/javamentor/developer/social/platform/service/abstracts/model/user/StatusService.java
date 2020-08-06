package com.javamentor.developer.social.platform.service.abstracts.model.user;

import com.javamentor.developer.social.platform.models.entity.user.Status;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

public interface StatusService extends GenericService<Status, Long> {

    Status getByStatusName(String name);
}
