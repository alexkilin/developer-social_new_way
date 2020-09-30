package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.VideoDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.VideoDto;
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
                        " v.media.url, v.name, v.icon, v.media.persistDateTime)" +
                        " FROM Videos as v WHERE v.media.mediaType = 2 AND v.media.user.userId = :userId", VideoDto.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<VideoDto> getPartVideoOfUser(Long userId, int currentPage, int itemsOnPage) {
        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.VideoDto(v.id," +
                        " v.media.url, v.name, v.icon, v.media.persistDateTime)" +
                        " FROM Videos as v WHERE v.media.mediaType = 2 AND v.media.user.userId = :userId", VideoDto.class)
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
        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.VideoDto(v.id," +
                        " v.media.url, v.name, v.icon, v.media.persistDateTime)" +
                        " FROM AlbumVideo av JOIN av.videos as v WHERE av.album.name =:album " +
                        "AND av.album.userOwnerId.userId = :userId", VideoDto.class)
                .setParameter("album", album)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public boolean addVideoInCollectionsOfUser(Long userId, Long videoId) {
        return false;
    }

    @Override
    public List<VideoDto> getVideoFromAlbumOfUser(Long albumId) {
        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.VideoDto(v.id," +
                        " v.media.url, v.name, v.icon, v.media.persistDateTime)" +
                        " FROM AlbumVideo av JOIN av.videos as v WHERE av.album.id =:albumId", VideoDto.class)
                .setParameter("albumId", albumId).getResultList();
    }
}
