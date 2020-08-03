package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupCategoryDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
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
        Query queryForPosts = (Query) entityManager.createQuery(
                "SELECT " +
                        "p.id, " +
                        "p.lastRedactionDate, " +
                        "p.persistDate, " +
                        "p.title, " +
                        "p.text, " +
                        "c.id, " +
                        "c.persistDate, " +
                        "c.lastRedactionDate, " +
                        "c.comment, " +
                        "(SELECT u.lastName FROM User u WHERE c.user.userId = u.userId), " +
                        "(SELECT u.firstName FROM User u WHERE c.user.userId = u.userId), " +
                        "m.mediaType, " +
                        "m.url " +
                    "FROM Group g " +
                        "LEFT JOIN GroupCategory gc ON g.groupCategory.id = gc.id " +
                        "LEFT JOIN g.posts p " +
                        "LEFT JOIN PostComment pc on p.id = pc.post.id " +
                        "LEFT JOIN Comment c on pc.comment.id = c.id " +
                        "LEFT JOIN p.media m " +
                    "WHERE g.id = :paramId")
                .setParameter("paramId", id);

        Query queryForGroup = (Query) entityManager.createQuery(
                "SELECT " +
                        "g.id, " +
                        "g.name, " +
                        "g.persistDate, " +
                        "g.linkSite, " +
                        "g.lastRedactionDate, " +
                        "gc.id, " +
                        "gc.category " +
                    "FROM Group g " +
                        "JOIN g.groupCategory gc " +
                    "WHERE g.id = :paramId")
                .setParameter("paramId", id);

        List<PostDto> postDtoList = (List<PostDto>) queryForPosts.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                MediaPostDto mediaPostDto = MediaPostDto.builder()
                        .mediaType(objects[11].toString())
                        .content((String) objects[12])
                        .build();
                List<MediaPostDto> mediaPostDtoList = new ArrayList<>();
                mediaPostDtoList.add(mediaPostDto);

                String userCommentFio = objects[9] + " " + objects[10];

                CommentDto commentDto = CommentDto.builder()
                        .comment((String) objects[8])
                        .id((Long) objects[5])
                        .lastRedactionDate((LocalDateTime) objects[7])
                        .persistDate((LocalDateTime) objects[6])
                        .userFio(userCommentFio)
                        .build();
                List<CommentDto> commentDtoList = new ArrayList<>();
                commentDtoList.add(commentDto);

                return PostDto.builder()
                        .id((Long) objects[0])
                        .lastRedactionDate((LocalDateTime) objects[1])
                        .media(mediaPostDtoList)
                        .persistDate((LocalDateTime) objects[2])
                        .text((String) objects[4])
                        .title((String) objects[3])
                        .comments(commentDtoList)
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getResultList();

        GroupDto groupDto = (GroupDto) queryForGroup.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                GroupCategoryDto groupCategoryDto = GroupCategoryDto.builder()
                        .category((String) objects[6])
                        .id((Long) objects[5])
                        .build();

                Set<PostDto> postDtoSet = new HashSet<>();

                return GroupDto.builder()
                        .id((Long) objects[0])
                        .name((String) objects[1])
                        .persistDate((LocalDateTime) objects[2])
                        .linkSite((String) objects[3])
                        .lastRedactionDate((LocalDateTime) objects[4])
                        .groupCategory(groupCategoryDto)
                        .posts(postDtoList)
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getSingleResult();

        return Optional.ofNullable(groupDto);
    }
}
