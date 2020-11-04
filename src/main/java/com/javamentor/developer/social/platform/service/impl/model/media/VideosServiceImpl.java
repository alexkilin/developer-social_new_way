package com.javamentor.developer.social.platform.service.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.VideosDao;
import com.javamentor.developer.social.platform.models.entity.media.Videos;
import com.javamentor.developer.social.platform.models.entity.user.User;
import com.javamentor.developer.social.platform.service.abstracts.model.media.VideosService;
import com.javamentor.developer.social.platform.service.impl.GenericServiceAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class VideosServiceImpl extends GenericServiceAbstract<Videos, Long> implements VideosService {

    private VideosDao videosDao;

    @Autowired
    public VideosServiceImpl(VideosDao dao) {
        super(dao);
        this.videosDao = dao;
    }

    @Override
    public void addVideoInCollectionsOfUser(User user, Long videoId) {
        Optional<Videos> video = videosDao.getById(videoId);
        if (!video.isPresent()) {
            throw new EntityNotFoundException(String.format("Видео с id %d не найдено", videoId));
        }
        Set<Videos> set = user.getVideos();
        set.add(video.get());
        user.setVideos(set);
    }
}
