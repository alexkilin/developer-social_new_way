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

@Component("getPartAudiosOfFriends")
public class PaginationGetPartAudioOfFriends implements PaginationDao<AudioDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetPartAudioOfFriends() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<AudioDto> getItems(Map<String, Object> parameters) {
        Long userId = (Long)parameters.get("userId");
        int currentPage = (int)parameters.get("currentPage");
        int itemsOnPage = (int)parameters.get("itemsOnPage");

        return (List<AudioDto>) entityManager
                .createQuery("SELECT " +
                        "c.id, " +
                        "c.icon, " +
                        "c.name, " +
                        "c.author, " +
                        "c.media.url, " +
                        "c.media.persistDateTime, " +
                        "c.album, " +
                        "c.length " +
                        "FROM User u join u.audios c join fetch Friend f on u.id=f.friend.id where f.user.id =:userId")
                .setParameter("userId", userId)
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
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (c) from User u join u.audios c join fetch Friend f on u.id=f.friend.id where f.user.id =:userId",
                Long.class
        ).setParameter("userId", parameters.get("userId"))
                .getSingleResult();
    }
}
