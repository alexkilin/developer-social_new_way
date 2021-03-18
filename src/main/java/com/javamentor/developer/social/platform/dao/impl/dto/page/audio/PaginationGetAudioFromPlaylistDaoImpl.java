package com.javamentor.developer.social.platform.dao.impl.dto.page.audio;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component("getAudioFromPlaylist")
public class PaginationGetAudioFromPlaylistDaoImpl implements PaginationDao<AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAudioFromPlaylistDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        Long playlistId = (Long) parameters.get("playlisyId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

       return entityManager.createQuery(
                "SELECT " +
                        "au.id, " +
                        "au.media.url, " +
                        "au.icon, " +
                        "au.name, " +
                        "au.author, " +
                        "au.album, " +
                        "au.length, " +
                        "au.media.persistDateTime, " +
                        "au.listening " +
                        "FROM Audios au " +
                        "JOIN au.playlists as pl " +
                        "WHERE pl.id = :playlistId " +
                        "ORDER BY au.id ASC"
                , AudioDto.class)
                .setParameter("playlistId", playlistId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .url((String) objects[1])
                                        .icon((String) objects[2])
                                        .name((String) objects[3])
                                        .author((String) objects[4])
                                        .album((String) objects[5])
                                        .length((Integer) objects[6])
                                        .persistDateTime((LocalDateTime) objects[7])
                                        .listening((Integer) objects[8])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                ).getResultList();

    }


    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (au) from Audios au join au.playlists as pl where pl.id = :playlistId",
                Long.class
        ).setParameter("playlistId", parameters.get("playlistId"))
                .getSingleResult();
    }
}
