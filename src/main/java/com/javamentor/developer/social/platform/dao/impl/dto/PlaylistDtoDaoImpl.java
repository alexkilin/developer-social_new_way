package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.PlaylistCreateDto;
import com.javamentor.developer.social.platform.models.dto.PlaylistGetDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PlaylistDtoDaoImpl implements PlaylistDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public PlaylistGetDto getById(Long id) {
        List<PlaylistGetDto> list = entityManager.createQuery("SELECT " +
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
                })
                .getResultList();
        return list.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PlaylistGetDto> getByUserId(Long userId) {
        List<PlaylistGetDto> list = entityManager.createQuery("SELECT " +
                "p.id," +
                "p.name, " +
                "p.image," +
                "p.ownerUser.userId," +
                "p.persistDateTime " +
                "FROM Playlist as p " +
                "WHERE p.ownerUser.userId = :userId")
                .setParameter("userId", userId)
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
                })
                .getResultList();
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAudioDtoByPlaylistId(Long playlistId) {
        Query queryTagsForPost = (Query) entityManager.createQuery(
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
                .setParameter("playlistId", playlistId);
        return queryTagsForPost.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

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


}
