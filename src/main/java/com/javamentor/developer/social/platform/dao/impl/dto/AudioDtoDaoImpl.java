package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class AudioDtoDaoImpl implements AudioDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Audios getAudioOfId(Long id) {
        return entityManager.find(Audios.class, id);
    }
}



























