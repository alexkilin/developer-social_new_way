package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GroupDtoDaoImpl implements GroupDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GroupInfoDto> getAllGroups(int page, int size) {
        Query query = (Query) entityManager.createQuery(
                "SELECT DISTINCT " +
                        "g.id, " +
                        "g.name, " +
                        "gc.category, " +
                        "(SELECT COUNT(ghu.id) FROM ghu WHERE ghu.group.id = g.id) " +
                    "FROM Group g JOIN GroupCategory gc ON gc.id = g.groupCategory.id " +
                        "JOIN GroupHasUser ghu ON g.id = ghu.group.id")
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        return (List<GroupInfoDto>) query.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                return GroupInfoDto.builder()
                        .id((Long) objects[0])
                        .name((String) objects[1])
                        .groupCategory((String) objects[2])
                        .subscribers((Long) objects[3])
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getResultList();
    }

    @Override
    public GroupDto getGroupById(Long id) {
        Query query = (Query) entityManager.createQuery(
                "SELECT " +
                        "g.id, " +
                        "g.name, " +
                        "gc.id , " +
                        "gc.category " +
                        "FROM Group g JOIN g.groupCategory gc WHERE g.id = :paramId")
                .setParameter("paramId", id);

        return null;
    }
}
