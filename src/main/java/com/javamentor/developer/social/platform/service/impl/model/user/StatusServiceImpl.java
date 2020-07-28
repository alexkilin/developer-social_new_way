package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.StatusDAO;
import com.javamentor.developer.social.platform.models.entity.user.Status;
import com.javamentor.developer.social.platform.service.abstracts.model.user.StatusService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl extends GenericServiceAbstract<Status, Long> implements StatusService {

    @Autowired
    public StatusServiceImpl(StatusDAO dao) {
        super(dao);
    }
}