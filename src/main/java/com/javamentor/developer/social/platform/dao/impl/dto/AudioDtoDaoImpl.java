package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AudioDtoDao;
import com.javamentor.developer.social.platform.models.dto.AudioDto;
import com.javamentor.developer.social.platform.models.entity.media.Audios;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Repository
public class AudioDtoDaoImpl implements AudioDtoDao {

    // Скорее всего мы не хотим делать через медиа дто, мы можем обратится напрямую к таблице аудио и получить все что нужно
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getAllAudios() {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, "+
                        "c.icon, " +
                        "c.name, "+
                        "c.author, "+
                        "c.media.url, "+
                        "c.media.persistDateTime "+
                        "FROM Audios as c")
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
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios.isEmpty() ? Collections.emptyList() : audios;
    }

    @Override
    public List<AudioDto> getPartAudio(int start, int end) { //вот тут будут выдоватся id не по порядку, так как выдаются id media, но я думаю фронты не обидятся
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, "+
                        "c.icon, " +
                        "c.name, "+
                        "c.author, "+
                        "c.media.url, "+
                        "c.media.persistDateTime "+
                        "FROM Audios as c")
                .setFirstResult(start)
                .setMaxResults(end-start)
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
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios.isEmpty() ? Collections.emptyList() : audios;
    }

    @Override
    public List<AudioDto> getAudioOfAuthor(String author) {
        List<AudioDto> audios = entityManager
                .createQuery("SELECT " +
                        "c.id, "+
                        "c.icon, " +
                        "c.name, "+
                        "c.author, "+
                        "c.media.url, "+
                        "c.media.persistDateTime "+
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
                                        .build();
                            }

                            @Override
                            public List transformList(List list) {
                                return list;
                            }
                        }
                )
                .getResultList();
        return audios.isEmpty() ? Collections.emptyList() : audios;
    }
}
