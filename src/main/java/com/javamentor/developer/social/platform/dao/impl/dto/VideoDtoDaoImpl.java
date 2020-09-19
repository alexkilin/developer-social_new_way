package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.VideoDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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
                " v.media.url, v.name, v.icon, v.media.persistDateTime)" +
                        " FROM Videos as v WHERE v.name = :name", VideoDto.class));
    }

    @Override
    public List<VideoDto> getVideoOfAlbum(String album) {
        return null;
    }

    @Override
    public List<VideoDto> getVideoOfUser(Long userId) {
        return null;
    }

    @Override
    public List<VideoDto> getPartVideoOfUser(Long userId, int currentPage, int itemsOnPage) {
        return null;
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
