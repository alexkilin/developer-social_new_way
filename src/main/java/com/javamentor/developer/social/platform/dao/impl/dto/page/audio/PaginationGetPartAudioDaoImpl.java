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

@Component("getPartAudios")
public class PaginationGetPartAudioDaoImpl implements PaginationDao<AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetPartAudioDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int)parameters.get("currentPage");
        int itemsOnPage = (int)parameters.get("itemsOnPage");

        return entityManager.createQuery(
                "SELECT " +
                        "a.id," +
                        "a.media.url, " +
                        "a.icon, " +
                        "a.name, " +
                        "a.author, " +
                        "a.album," +
                        "a.length," +
                        "a.media.persistDateTime, " +
                        "a.listening " +
                        "FROM Audios as a " +
                        "WHERE a.media.mediaType = 1 " +
                        "ORDER BY a.id ASC")
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(
                        new ResultTransformer(){
                            @Override
                            public Object transformTuple(Object[] objects, String[] strings) {
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
                        }).getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (a) from  Audios as a where a.media.mediaType = 1",
                Long.class
        ).getSingleResult();
    }
}
