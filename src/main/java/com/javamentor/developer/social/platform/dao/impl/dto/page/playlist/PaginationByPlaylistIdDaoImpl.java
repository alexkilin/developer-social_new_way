package com.javamentor.developer.social.platform.dao.impl.dto.page.playlist;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component("getAudioFromPlaylistById")
public class PaginationByPlaylistIdDaoImpl implements PaginationDao<AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationByPlaylistIdDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        Long playlistId = (Long)parameters.get("playlistId");
        int currentPage = (int)parameters.get("currentPage");
        int itemsOnPage = (int)parameters.get("itemsOnPage");

        return entityManager.createQuery(
                "SELECT " +
                        "c.id," +
                        "c.media.url," +
                        "c.icon," +
                        "c.name," +
                        "c.author," +
                        "c.album," +
                        "c.media.persistDateTime " +
                        "FROM Playlist p " +
                        "JOIN p.playlistContent c " +
                        "WHERE p.id = :playlistId")
                .setParameter("playlistId", playlistId)
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                return AudioDto.builder()
                        .id((Long) objects[0])
                        .url((String) objects[1])
                        .icon((String) objects[2])
                        .name((String) objects[3])
                        .author((String) objects[4])
                        .album((String) objects[5])
                        .persistDateTime((LocalDateTime) objects[6])
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (c) from Playlist p " +
                        "join p.playlistContent c where p.id = :playlistId",
                Long.class
        ).setParameter("playlistId", parameters.get("playlistId"))
                .getSingleResult();
    }
}

