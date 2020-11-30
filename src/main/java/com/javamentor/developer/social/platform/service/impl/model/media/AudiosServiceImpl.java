package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.AudiosDao;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.media.AudiosService;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class AudiosServiceImpl extends GenericServiceAbstract<Audios, Long> implements AudiosService {

    private final UserService userService;
    private final AudiosDao audiosDao;

    @Autowired
    public AudiosServiceImpl(AudiosDao dao, UserService userService) {
        super(dao);
        this.audiosDao = dao;
        this.userService = userService;
    }

    @Override
    public void addAudioInCollectionsOfUser(User user, Long audioId) {
        Optional<Audios> audios = audiosDao.getById(audioId);
        if(!audios.isPresent()){
            throw new EntityNotFoundException("Audio with id " + audioId + " not found");
        }
        Set<Audios> set = user.getAudios();
        set.add(audios.get());
        user.setAudios(set);
        userService.update(user);
    }
}
