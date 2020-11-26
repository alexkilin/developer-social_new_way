package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class VideoDtoDaoImpl implements VideoDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<VideoDto> getVideoOfName(String name) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.media.video.VideoDto(v.id," +
                " m.url, v.name, v.icon, v.author, m.persistDateTime)" +
                        " FROM Videos as v JOIN Media as m ON v.media.id = m.id WHERE v.name = :name", VideoDto.class)
                .setParameter("name", name));
    }

    @Override
    public boolean addVideoInCollectionsOfUser(Long userId, Long videoId) {
        return false;
    }
}
