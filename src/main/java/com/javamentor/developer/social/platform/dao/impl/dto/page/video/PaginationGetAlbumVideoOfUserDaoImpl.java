package com.javamentor.developer.social.platform.dao.impl.dto.page.video;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAlbumVideoOfUser")
public class PaginationGetAlbumVideoOfUserDaoImpl implements PaginationDao<VideoDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAlbumVideoOfUserDaoImpl() {
    }

    @Override
    public List<VideoDto> getItems(Map<String, Object> parameters) {
        Long userId = (Long) parameters.get("userId");
        String album = (String) parameters.get("album");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.media.video.VideoDto(v.id," +
                        " v.media.url, v.name, v.icon, v.author, v.media.persistDateTime, count (ml.like.id), v.view)" +
                        " FROM AlbumVideo av JOIN av.videos as v left join MediaLike as ml on v.media.id = ml.media.id " +
                        "WHERE av.album.name =:album AND av.album.userOwnerId.userId = :userId " +
                        "GROUP BY v.media.id, v.media.url, v.media.persistDateTime " +
                        "ORDER BY v.id ASC", VideoDto.class)
                .setParameter("album", album)
                .setParameter("userId", userId)
                .setFirstResult((currentPage - 1)* itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (v) from AlbumVideo av " +
                        "join av.videos as v where av.album.name =:album " +
                        "and av.album.userOwnerId.userId = :userId",
                Long.class
        ).setParameter("album", parameters.get("album"))
                .setParameter("userId", parameters.get("userId"))
                .getSingleResult();
    }
}
