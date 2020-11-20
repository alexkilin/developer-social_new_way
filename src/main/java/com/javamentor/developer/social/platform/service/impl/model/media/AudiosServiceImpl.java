package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.AudiosDao;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AudiosService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class AudiosServiceImpl extends GenericServiceAbstract<Audios, Long> implements AudiosService {

    private final AudiosDao audiosDao;

    @Autowired
    public AudiosServiceImpl(AudiosDao dao) {
        super(dao);
        this.audiosDao = dao;
    }

    @Override
    @Transactional
    public boolean addAudioInCollectionsOfUser(User user, Long audioId) {
        Optional<Audios> audios = audiosDao.getById(audioId);
        if (!audios.isPresent()) {
            return false;
        }
        Set<Audios> set = user.getAudios();
        set.add(audios.get());
        user.setAudios(set);
        return true;
    }
}
