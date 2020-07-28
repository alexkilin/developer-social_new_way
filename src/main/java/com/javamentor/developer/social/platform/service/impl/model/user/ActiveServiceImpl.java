package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.ActiveDAO;
import com.javamentor.developer.social.platform.dao.impl.model.user.ActiveDAOImpl;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.service.abstracts.model.user.ActiveService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveServiceImpl extends GenericServiceAbstract<Active, Long> implements ActiveService {

    @Autowired
    public ActiveServiceImpl(ActiveDAO dao) {
        super(dao);
    }
}
