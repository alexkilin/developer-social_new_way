package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.music.PlaylistGetDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class PlaylistDtoDaoImpl implements PlaylistDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<PlaylistGetDto> getById(Long id) {
        Query<PlaylistGetDto> query = entityManager.createQuery("SELECT " +
                "p.id," +
                "p.name, " +
                "p.image," +
                "p.ownerUser.userId," +
                "p.persistDateTime " +
                "FROM Playlist as p " +
                "WHERE p.id = :id")
                .setParameter("id", id)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return PlaylistGetDto.builder()
                                .id((Long) objects[0])
                                .name((String) objects[1])
                                .image((String) objects[2])
                                .ownerUserId((Long) objects[3])
                                .persistDateTime((LocalDateTime) objects[4])
                                .build();
                    }

                    @Override
                    public List transformList(List collection) {
                        return collection;
                    }
                });

        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAudioDtoByPlaylistId(List<Long> playlistId) {
        return  entityManager.createQuery(
                "SELECT " +
                        "c.id," +
                        "c.media.url," +
                        "c.icon," +
                        "c.name," +
                        "c.author," +
                        "c.album," +
                        "c.media.persistDateTime," +
                        "p.id " +
                        "FROM Playlist p " +
                        "JOIN p.playlistContent c " +
                        "WHERE p.id in (:playlistId) " +
                        "ORDER BY p.id ASC")
                .setParameter("playlistId", playlistId)
                .unwrap(Query.class).setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        if(objects[0] != null && objects[1] != null && objects[7] != null) {
                            return AudioDto.builder()
                                    .id((Long) objects[0])
                                    .url((String) objects[1])
                                    .icon((String) objects[2])
                                    .name((String) objects[3])
                                    .author((String) objects[4])
                                    .album((String) objects[5])
                                    .persistDateTime((LocalDateTime) objects[6])
                                    .playlistId((Long) objects[7])
                                    .build();
                        } else return null;
                    }
                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }).getResultList();
    }
}
