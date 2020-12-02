package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class GroupDtoDaoImpl implements GroupDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<GroupDto> getGroupById(Long id) {
        Query<GroupDto> queryForGroup = entityManager.createQuery(
                "SELECT " +
                        "g.id, " +
                        "g.name, " +
                        "g.persistDate, " +
                        "g.linkSite, " +
                        "g.lastRedactionDate, " +
                        "gc.category, " +
                        "u.firstName, " +
                        "u.lastName, " +
                        "g.addressImageGroup," +
                        "g.description, " +
                        "(SELECT COUNT(ghu.id) FROM GroupHasUser ghu WHERE ghu.group.id = g.id) " +
                    "FROM Group g " +
                        "JOIN g.groupCategory gc " +
                        "JOIN g.owner u " +
                    "WHERE g.id = :paramId")
                .setParameter("paramId", id)
                .unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                String userOwnerFio =  objects[7] + " " + objects[6];

                return GroupDto.builder()
                        .id((Long) objects[0])
                        .name((String) objects[1])
                        .persistDate((LocalDateTime) objects[2])
                        .linkSite((String) objects[3])
                        .lastRedactionDate((LocalDateTime) objects[4])
                        .groupCategory((String) objects[5])
                        .ownerFio(userOwnerFio)
                        .addressImageGroup((String) objects[8])
                        .description((String) objects[9])
                        .subscribers((Long) objects[10])
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        });

        return SingleResultUtil.getSingleResultOrNull(queryForGroup);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<GroupDto> getGroupByName(String name) {
        Query<GroupDto> queryForGroup = entityManager.createQuery(
                "SELECT " +
                        "g.id, " +
                        "g.name, " +
                        "g.persistDate, " +
                        "g.linkSite, " +
                        "g.lastRedactionDate, " +
                        "gc.category, " +
                        "u.firstName, " +
                        "u.lastName, " +
                        "g.description, " +
                        "(SELECT COUNT(ghu.id) FROM GroupHasUser ghu WHERE ghu.group.id = g.id), " +
                        "g.addressImageGroup " +
                        "FROM Group g " +
                        "JOIN g.groupCategory gc " +
                        "JOIN g.owner u " +
                        "WHERE g.name = :name")
                .setParameter("name", name)
                .unwrap(Query.class).setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        String userOwnerFio =  objects[7] + " " + objects[6];

                        return GroupDto.builder()
                                .id((Long) objects[0])
                                .name((String) objects[1])
                                .persistDate((LocalDateTime) objects[2])
                                .linkSite((String) objects[3])
                                .lastRedactionDate((LocalDateTime) objects[4])
                                .groupCategory((String) objects[5])
                                .ownerFio(userOwnerFio)
                                .description((String) objects[8])
                                .subscribers((Long) objects[9])
                                .addressImageGroup((String) objects[10])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                });

        return SingleResultUtil.getSingleResultOrNull(queryForGroup);
    }
}
