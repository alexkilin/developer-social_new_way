package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.GenericDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.media.AudiosDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.user.UserDao;
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


    @Override
    public boolean addAudioInCollectionsOfUser(User user, Long audioId) {
       Audios audios = audiosDao.getById(audioId);
       if(audios==null){
           return false;
       }
        Set<Audios> set = user.getAudios();
        set.add(audios);
        user.setAudios(set);
        return true;

    }
}
