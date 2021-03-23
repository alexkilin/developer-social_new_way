package com.javamentor.developer.social.platform.dao.impl.dto.page.audio;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.media.music.AudioDto;
import com.javamentor.developer.social.platform.models.dto.media.video.VideoDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component("getAudioSortedByListening")
public class PaginationGetAudioSortedByListeningDaoImpl implements PaginationDao<AudioDto> {

    @PersistenceContext
    EntityManager entityManager;

    public PaginationGetAudioSortedByListeningDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        return (List<AudioDto>) entityManager.createQuery(
                "SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length, " +
                        "c.listening " +
                        "FROM Audios c " +
                        "ORDER BY c.listening DESC")
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer(){
                            @Override
                            public Object transformTuple(Object[] objects, String[] strings) {
                                return AudioDto.builder()
                                        .id(((Number) objects[0]).longValue())
                                        .icon((String) objects[1])
                                        .name((String) objects[2])
                                        .author((String) objects[3])
                                        .url((String) objects[4])
                                        .persistDateTime((LocalDateTime) objects[5])
                                        .album((String) objects[6])
                                        .length((Integer) objects[7])
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
                "select count (v) FROM Audios v ",
                Long.class
        ).getSingleResult();
    }
}