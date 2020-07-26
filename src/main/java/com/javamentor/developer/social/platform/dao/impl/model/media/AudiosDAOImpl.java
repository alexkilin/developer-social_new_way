package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.AudiosDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import org.springframework.stereotype.Repository;

@Repository
public class AudiosDAOImpl extends GenericDaoAbstract<Audios, Long> implements AudiosDAO {
}
