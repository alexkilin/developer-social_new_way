package com.javamentor.developer.social.platform.dao.abstracts.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.models.entity.media.Audios;

import java.util.Optional;

public interface AudiosDao extends GenericDao<Audios, Long> {

    Optional<Audios> getByIdWithMedia(Long id);
}
