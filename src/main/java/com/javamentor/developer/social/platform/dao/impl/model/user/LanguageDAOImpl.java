package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.model.user.LanguageDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.user.Language;
import org.springframework.stereotype.Repository;

@Repository
public class LanguageDAOImpl extends GenericDaoAbstract<Language, Long> implements LanguageDAO {
}
