package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupDtoDaoImpl implements GroupDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
    public Optional<GroupDto> getGroupById(Long id) {
        Query queryForGroup = (Query) entityManager.createQuery(
                "SELECT " +
                        "g.id, " +
                        "g.name, " +
                        "g.persistDate, " +
                        "g.linkSite, " +
                        "g.lastRedactionDate, " +
                        "gc.category, " +
                        "u.firstName, " +
                        "u.lastName, " +
                        "g.description " +
                    "FROM Group g " +
                        "JOIN g.groupCategory gc " +
                        "JOIN g.owner u " +
                    "WHERE g.id = :paramId")
                .setParameter("paramId", id);

        GroupDto groupDto = (GroupDto) queryForGroup.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

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
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getSingleResult();

        return Optional.ofNullable(groupDto);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<GroupWallDto> getPostsByGroupId(Long id, int page, int size) {
        Query queryPostsForGroup = (Query) entityManager.createQuery(
                "SELECT " +
                        "p.id, " +
                        "p.lastRedactionDate, " +
                        "p.persistDate, " +
                        "p.title, " +
                        "p.text, " +
                        "(SELECT COUNT(pc) FROM PostComment pc WHERE p.id = pc.post.id), " +
                        "(SELECT COUNT(pl) FROM PostLike pl WHERE p.id = pl.post.id) " +
                     "FROM Group g " +
                        "LEFT JOIN g.posts p " +
                    "WHERE g.id = :paramId")
                .setParameter("paramId", id)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        return queryPostsForGroup.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                return GroupWallDto.builder()
                        .countComments((Long) objects[5])
                        .id((Long) objects[0])
                        .lastRedactionDate((LocalDateTime) objects[1])
                        .persistDate((LocalDateTime) objects[2])
                        .text((String) objects[4])
                        .title((String) objects[3])
                        .countLikes((Long) objects[6])
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getResultList();
    }
}
