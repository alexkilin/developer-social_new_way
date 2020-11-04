package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PlaylistDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.dto.PlaylistGetDto;
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
    public List<PlaylistGetDto> getByUserId(Long userId) {
        List<PlaylistGetDto> list = entityManager.createQuery("SELECT " +
                "p.id," +
                "p.name, " +
                "p.image," +
                "p.ownerUser.userId," +
                "p.persistDateTime, " +
                "c.id," +
                "c.media.url," +
                "c.icon," +
                "c.name," +
                "c.author," +
                "c.album," +
                "c.media.persistDateTime, " +
                "c.length " +
                "FROM Playlist as p " +
                "LEFT JOIN p.playlistContent as c " +
                "WHERE p.ownerUser.userId = :userId")
                .setParameter("userId", userId)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {

                        AudioDto audioDto = AudioDto.builder()
                                .id((Long) objects[5])
                                .url((String) objects[6])
                                .icon((String) objects[7])
                                .name((String) objects[8])
                                .author((String) objects[9])
                                .album((String) objects[10])
                                .persistDateTime((LocalDateTime) objects[11])
                                .length((Integer) objects[12])
                                .build();

                        List<AudioDto> audioDtoList = new ArrayList<>();
                        audioDtoList.add(audioDto);

                        return PlaylistGetDto.builder()
                                .id((Long) objects[0])
                                .name((String) objects[1])
                                .image((String) objects[2])
                                .ownerUserId((Long) objects[3])
                                .persistDateTime((LocalDateTime) objects[4])
                                .content(audioDtoList)
                                .build();
                    }

                    @Override
                    public List transformList(List collection) {
                        Map<Long, PlaylistGetDto> playlistGetDtoMap = new TreeMap<>();
                        Map<Long, List<AudioDto>> audioDtoMap = new TreeMap<>();

                        for (Object obj : collection) {
                            PlaylistGetDto playlistGetDto = (PlaylistGetDto) obj;
                            Long playlistGetDtoId = playlistGetDto.getId();

                            List<AudioDto> audioDtoList = audioDtoMap.put(playlistGetDtoId, playlistGetDto.getContent());
                            if (audioDtoList != null) {
                                audioDtoList.addAll(playlistGetDto.getContent());
                                audioDtoMap.put(playlistGetDtoId, audioDtoList);
                            }
                            playlistGetDto.setContent(audioDtoMap.get(playlistGetDtoId));

                            playlistGetDtoMap.put(playlistGetDtoId, playlistGetDto);
                        }
                        return new ArrayList<>(playlistGetDtoMap.values());
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
