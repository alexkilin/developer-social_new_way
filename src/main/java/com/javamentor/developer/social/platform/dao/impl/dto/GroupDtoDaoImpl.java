package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
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
                        "(SELECT COUNT(ghu.id) FROM ghu WHERE ghu.group.id = g.id), " +
                        "g.addressImageGroup " +
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
                        .addressImageGroup((String) objects[4])
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
    public List<GroupWallDto> getPostsByGroupId(Long id, int page, int size) {
        Query queryPostsForGroup = (Query) entityManager.createQuery(
                "SELECT " +
                        "p.id, " +
                        "p.lastRedactionDate, " +
                        "p.persistDate, " +
                        "p.title, " +
                        "p.text, " +
                        "(SELECT COUNT(pc) FROM PostComment pc WHERE p.id = pc.post.id), " +
                        "(SELECT COUNT(pl) FROM PostLike pl WHERE p.id = pl.post.id), " +
                        "(SELECT COUNT(bm) FROM Bookmark bm WHERE bm.post.id = p.id), " +
                        "(SELECT COUNT(rp) FROM p.reposts rp), " +
                        "m.mediaType, " +
                        "m.url, " +
                        "t.id," +
                        "t.text, " +
                        "u.userId " +
                        "FROM Group g " +
                        "LEFT JOIN g.posts p " +
                        "LEFT JOIN p.user u " +
                        "LEFT JOIN p.media m " +
                        "LEFT JOIN p.tags t " +
                        "WHERE g.id = :paramId")
                .setParameter("paramId", id)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        return queryPostsForGroup.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                MediaPostDto mediaPostDto = MediaPostDto.builder()
                        .userId((Long) objects[13])
                        .mediaType(objects[9] == null ? "null" : objects[9].toString())
                        .url((String) objects[10])
                        .build();
                List<MediaPostDto> mediaPostDtoList = new ArrayList<>();
                if (!mediaPostDto.getMediaType().equals("null")) {
                    mediaPostDtoList.add(mediaPostDto);
                }

                TagDto tagDto = TagDto.builder()
                        .id(objects[11] == null ? 0 : (Long) objects[11])
                        .text(objects[12] == null ? "null" : (String) objects[12])
                        .build();
                List<TagDto> tagDtoList = new ArrayList<>();
                if (!tagDto.getText().equals("null")) {
                    tagDtoList.add(tagDto);
                }

                return GroupWallDto.builder()
                        .countComments((Long) objects[5])
                        .id((Long) objects[0])
                        .lastRedactionDate((LocalDateTime) objects[1])
                        .persistDate((LocalDateTime) objects[2])
                        .text((String) objects[4])
                        .title((String) objects[3])
                        .countLikes((Long) objects[6])
                        .countBookmarks((Long) objects[7])
                        .countReposts((Long) objects[8])
                        .media(mediaPostDtoList)
                        .tags(tagDtoList)
                        .build();
            }

            @Override
            public List transformList(List list) {
                Map<Long, GroupWallDto> groupWallDtoMap = new TreeMap<>();
                Map<Long, List<MediaPostDto>> mediaPostDtoMap = new TreeMap<>();
                Map<Long, List<TagDto>> tagDtoMap = new TreeMap<>();

                for (Object obj : list) {
                    GroupWallDto groupWallDto = (GroupWallDto) obj;
                    Long groupWallDtoId = groupWallDto.getId();

                    List<MediaPostDto> mediaPostDtoList
                            = mediaPostDtoMap.put(groupWallDtoId, groupWallDto.getMedia());
                    if (mediaPostDtoList != null) {
                        mediaPostDtoList.addAll(groupWallDto.getMedia());
                        mediaPostDtoMap.put(groupWallDtoId, mediaPostDtoList);
                    }
                    groupWallDto.setMedia(mediaPostDtoMap.get(groupWallDtoId));

                    List<TagDto> tagDtoList = tagDtoMap.put(groupWallDtoId, groupWallDto.getTags());
                    if (tagDtoList != null) {
                        tagDtoList.removeAll(groupWallDto.getTags());
                        tagDtoList.addAll(groupWallDto.getTags());
                        tagDtoMap.put(groupWallDtoId, tagDtoList);
                    }
                    groupWallDto.setTags(tagDtoMap.get(groupWallDtoId));

                    groupWallDtoMap.put(groupWallDtoId, groupWallDto);
                }
                return new ArrayList<>(groupWallDtoMap.values());
            }
        }).getResultList();
    }


    @Override
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

    @Override
    public List<UserDto> getUsersFromTheGroup(Long id, int page, int size) {
        Query<UserDto> queryUsersFromTheGroup = (Query<UserDto>) entityManager.createQuery(
                "SELECT " +
                        "u.userId, " +
                        "u.firstName, " +
                        "u.lastName, " +
                        "u.dateOfBirth, " +
                        "u.education, " +
                        "u.aboutMe, " +
                        "u.avatar, " +
                        "u.email, " +
                        "u.password, " +
                        "u.city, " +
                        "u.linkSite, "+
                        "u.role.name, " +
                        "u.status, " +
                        "u.active.name" +
                        " FROM User u join GroupHasUser g ON u.userId = g.user.userId WHERE g.group.id = :id")
                .setParameter("id", id)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size);
        return (List<UserDto>) queryUsersFromTheGroup.unwrap(Query.class).setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return UserDto.builder()
                                .userId(((Number) objects[0]).longValue())
                                .firstName((String) objects[1])
                                .lastName((String) objects[2])
                                .dateOfBirth((Date) objects[3])
                                .education((String) objects[4])
                                .aboutMe((String) objects[5])
                                .avatar((String) objects[6])
                                .email((String) objects[7])
                                .password((String) objects[8])
                                .city((String) objects[9])
                                .linkSite((String) objects[10])
                                .roleName(((String) objects[11]))
                                .status((String) objects[12])
                                .activeName((String) objects[13]).build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }).getResultList();
    }
}
