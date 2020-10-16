package com.javamentor.developer.social.platform.service.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.LanguageDao;
import com.javamentor.developer.social.platform.models.entity.user.Language;
import com.javamentor.developer.social.platform.service.abstracts.model.user.LanguageService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl extends GenericServiceAbstract<Language, Long> implements LanguageService {

    @Autowired
    public LanguageServiceImpl(LanguageDao dao) {
        super(dao);
    }
}
