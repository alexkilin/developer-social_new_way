package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.LanguageDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Language;
import org.springframework.stereotype.Repository;

@Repository
public class LanguageDaoImpl extends GenericDaoAbstract<Language, Long> implements LanguageDao {
}
