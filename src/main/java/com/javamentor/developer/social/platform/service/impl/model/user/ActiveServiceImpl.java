package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.ActiveDao;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.service.abstracts.model.user.ActiveService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActiveServiceImpl extends GenericServiceAbstract<Active, Long> implements ActiveService {

    private final ActiveDao activeDAO;

    @Autowired
    public ActiveServiceImpl(ActiveDao dao, ActiveDao activeDAO) {
        super(dao);
        this.activeDAO = activeDAO;
    }


    @Override
    public Optional<Active> getByActiveName(String active) {
        return activeDAO.getByActiveName(active);
    }
}
