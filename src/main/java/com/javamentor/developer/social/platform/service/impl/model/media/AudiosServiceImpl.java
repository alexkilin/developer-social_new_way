package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.media.AudiosDAO;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AudiosService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AudiosServiceImpl extends GenericServiceAbstract<Audios, Long> implements AudiosService {

    private AudiosDAO audiosDao;

    @Autowired
    public AudiosServiceImpl(AudiosDAO dao) {
        super(dao);
        this.audiosDao = dao;
    }


    @Override
    public Optional<Audios> getOptionalById(Long id) {
        return Optional.ofNullable(getById(id));
    }
}
