package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.VideoDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class VideoDtoDaoImpl implements VideoDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<VideoDto> getVideoOfAuthor(String author) {

        return null;
    }

    @Override
    public Optional<VideoDto> getVideoOfName(String name) {
        return SingleResultUtil.getSingleResultOrNull(entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.VideoDto(v.id," +
                " m.url, v.name, v.icon, m.persistDateTime)" +
                        " FROM Videos as v JOIN Media as m ON v.media.id = m.id WHERE v.name = :name", VideoDto.class)
                .setParameter("name", name));
    }

    @Override
    public List<VideoDto> getVideoOfAlbum(String album) {
        return null;
    }

    @Override
    public List<VideoDto> getVideoOfUser(Long userId) {
        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.VideoDto(v.id," +
                        " m.url, v.name, v.icon, m.persistDateTime)" +
                        " FROM Videos as v JOIN Media as m ON v.media.id = m.id WHERE m.user.userId = :userId", VideoDto.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<VideoDto> getPartVideoOfUser(Long userId, int currentPage, int itemsOnPage) {
        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.VideoDto(v.id," +
                        " m.url, v.name, v.icon, m.persistDateTime)" +
                        " FROM Videos as v JOIN Media as m ON v.media.id = m.id WHERE m.user.userId = :userId", VideoDto.class)
                .setParameter("userId", userId)
                .setFirstResult(currentPage * itemsOnPage)
                .setMaxResults(itemsOnPage).getResultList();
    }

    @Override
    public List<VideoDto> getAuthorVideoOfUser(Long userId, String author) {
        return null;
    }

    @Override
    public List<VideoDto> getAlbumVideoOfUser(Long userId, String album) {
        return null;
    }

    @Override
    public boolean addVideoInCollectionsOfUser(Long userId, Long videoId) {
        return false;
    }

    @Override
    public List<VideoDto> getVideoFromAlbumOfUser(Long albumId) {
        return null;
    }
}
