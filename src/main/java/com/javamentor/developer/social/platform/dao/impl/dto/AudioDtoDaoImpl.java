package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AudioDtoDaoImpl implements AudioDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAudioOfAuthor(String author) {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM Audios as c WHERE c.author = :author")
                .setParameter("author", author)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAudioOfName(String name) {
        return entityManager.createQuery(
                "SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM Audios c " +
                        "WHERE upper(c.name) " +
                        "LIKE upper(:name) ")
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Audios getAudioOfId(Long id) {
        return entityManager.find(Audios.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAudioOfAlbum(String album) {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM Audios as c WHERE c.album = :album")
                .setParameter("album", album)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAudioOfUser(Long userId) {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM User u join u.audios c where u.userId =:userId")
                .setParameter("userId", userId)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getPartAudioOfUser(Long userId, int currentPage, int itemsOnPage) {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM User u join u.audios c where u.userId =:userId order by c.id asc")
                .setParameter("userId", userId)
                .setFirstResult(currentPage * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAuthorAudioOfUser(Long userId, String author) {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM User u join u.audios c where u.userId =:userId and c.author =:author")
                .setParameter("userId", userId)
                .setParameter("author", author)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAlbumAudioOfUser(Long userId, String album) {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM User u join u.audios c where  c.album =:album and u.userId =:userId")
                .setParameter("userId", userId)
                .setParameter("album", album)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios;
    }

    @Override
    public List<AudioDto> getAudioFromAlbumOfUser(Long albumId) {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM AlbumAudios u join u.audios as c where u.album.id =:albumId")
                .setParameter("albumId", albumId)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer() {
                            @Override
                            public Object transformTuple(
                                    Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios;
    }


    @Override
    public List<AudioDto> getAudioFromPlaylist(Long playlistId, int offset, int limit) {
        Query<AudioDto> query = (Query<AudioDto>) entityManager.createQuery(
                "SELECT NEW com.javamentor.developer.social.platform.models.dto.media.music.AudioDto( " +
                        "au.id, " +
                        "au.media.url, " +
                        "au.icon, " +
                        "au.name, " +
                        "au.author, " +
                        "au.album, " +
                        "au.length, " +
                        "au.media.persistDateTime) " +
                        "FROM Audios au " +
                        "JOIN au.playlists as pl " +
                        "WHERE pl.id = :playlistId"
                        , AudioDto.class)
                .setParameter("playlistId", playlistId)
                .setFirstResult(offset)
                .setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<AudioDto> getPartAudio(int currentPage, int itemsOnPage) {
        return entityManager.createQuery(
                "SELECT new com.javamentor.developer.social.platform.models.dto.media.music.AudioDto(" +
                        "a.id," +
                        "a.media.url, " +
                        "a.icon, " +
                        "a.name, " +
                        "a.author, " +
                        "a.album," +
                        "a.length," +
                        "a.media.persistDateTime)" +
                        "FROM Audios as a WHERE a.media.mediaType = 1", AudioDto.class)
                .setFirstResult(currentPage * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .getResultList();
    }
}



























