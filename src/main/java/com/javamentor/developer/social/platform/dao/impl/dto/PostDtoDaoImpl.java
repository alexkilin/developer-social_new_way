package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.service.abstracts.model.user.UserService;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;


@Repository
public class PostDtoDaoImpl implements PostDtoDao {

    private final EntityManager entityManager;
    private final UserService userService;

    @Autowired
    public PostDtoDaoImpl(EntityManager entityManager, UserService userService) {
        this.entityManager = entityManager;
        this.userService = userService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PostDto> getPostById(Long postId, Long userPrincipalId) {
        return (List<PostDto>) entityManager.createQuery(SELECT_ALL_POSTS + FROM_POST +
                "where p.id = :postId")
                .setParameter("userPrincipalId", userPrincipalId)
                .setParameter("postId", postId)
                .unwrap(Query.class)
                .setResultTransformer(getResultTransformer())
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MediaPostDto> getMediasByPostId(List<Long> postId) {
        Query queryMediasForPost = (Query) entityManager.createQuery(
                "SELECT " +
                        "m.id, " +
                        "m.mediaType, " +
                        "m.url, " +
                        "m.user.userId," +
                        "p.id " +
                        "FROM Post p " +
                        "LEFT JOIN p.media m " +
                        "WHERE p.id in (:postId)")
                .setParameter("postId", postId);
        return queryMediasForPost.unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        if (objects[0] != null && objects[1] != null && objects[2] != null) {
                            return MediaPostDto.builder()
                                    .id((Long) objects[0])
                                    .url((String) objects[2])
                                    .mediaType(objects[1] == null ? null : objects[0].toString())
                                    .userId((Long) objects[3])
                                    .postId(objects[4] == null ? null : (Long) objects[4])
                                    .build();
                        } else return null;
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                }).getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TagDto> getTagsByPostId(List<Long> postId) {
        Query queryTagsForPost = (Query) entityManager.createQuery(
                "SELECT " +
                        "t.text, " +
                        "t.id, " +
                        "p.id " +
                        "FROM Post p " +
                        "LEFT JOIN p.tags t " +
                        "WHERE p.id in (:postId)")
                .setParameter("postId", postId);
        return queryTagsForPost.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                if (objects[0] != null && objects[1] != null) {
                    return TagDto.builder()
                            .id((Long) objects[1])
                            .text((String) objects[0])
                            .postId(objects[2] == null ? null : (Long) objects[2])
                            .build();
                } else return null;
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getResultList();
    }

    private static final String SELECT_ALL_POSTS = "select " +
            "p.id, " +
            "p.title, " +
            "p.text, " +
            "u.userId, " +
            "u.firstName, " +
            "u.lastName, " +
            "u.avatar, " +
            "p.lastRedactionDate, " +
            "p.persistDate, " +
            "m.id," +
            "m.user.userId," +
            "m.mediaType," +
            "m.url," +
            "t.id," +
            "t.text," +
            "(select count(b.id) from Bookmark as b where b.post.id = p.id), " +
            "(select count(l.id) from PostLike as l where l.post.id = p.id), " +
            "(select count(c.id) from PostComment as c where c.post.id = p.id), " +
            "(select count(r.id) from Repost as r where r.post.id = p.id), " +
            "case when exists " +
            "(select pl from PostLike as pl where pl.like.user.userId = :userPrincipalId and pl.post.id = p.id)" +
            "then true else false end," +
            "case when exists " +
            "(select bm from Bookmark as bm where bm.user.userId = :userPrincipalId and bm.post.id = p.id)" +
            "then true else false end," +
            "case when exists " +
            "(select rp from Repost as rp where rp.user.userId = :userPrincipalId and rp.post.id = p.id)" +
            "then true else false end ";

    private static final String FROM_POST =
            "from Post as p " +
                    "join p.user as u " +
                    "left join p.tags as t " +
                    "left join p.media as m ";

    private ResultTransformer getResultTransformer() {
        return new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                List<MediaPostDto> mediaPostDtoList = getMediaPostDtoList(objects);
                List<TagDto> tagDtoList = getTagDtoList(objects);

                return getPostDto(objects, mediaPostDtoList, tagDtoList);
            }

            @Override
            public List<PostDto> transformList(List list) {
                return getTransformList(list);
            }
        };
    }

    private List<MediaPostDto> getMediaPostDtoList(Object[] objects) {
        List<MediaPostDto> mediaPostDtoList = new ArrayList<>();
        if (objects[9] != null) {
            MediaPostDto mediaPostDto = MediaPostDto.builder()
                    .id((Long) objects[9])
                    .userId((Long) objects[10])
                    .mediaType(objects[11] == null ? "null" : objects[17].toString())
                    .url((String) objects[12])
                    .build();
            mediaPostDtoList.add(mediaPostDto);
        }
        return mediaPostDtoList;
    }

    private List<TagDto> getTagDtoList(Object[] objects) {
        List<TagDto> tagDtoList = new ArrayList<>();
        if (objects[13] != null) {
            TagDto tagDto = TagDto.builder()
                    .id((Long) objects[13])
                    .text(objects[14] == null ? "null" : (String) objects[14])
                    .build();
            tagDtoList.add(tagDto);
        }
        return tagDtoList;
    }

    private PostDto getPostDto(Object[] objects,
                               List<MediaPostDto> mediaPostDtoList,
                               List<TagDto> tagDtoList) {

        return PostDto.builder()
                .id((Long) objects[0])
                .title((String) objects[1])
                .text((String) objects[2])
                .userId((Long) objects[3])
                .firstName((String) objects[4])
                .lastName((String) objects[5])
                .avatar((String) objects[6])
                .lastRedactionDate((LocalDateTime) objects[7])
                .persistDate((LocalDateTime) objects[8])
                .media(mediaPostDtoList)
                .tags(tagDtoList)
                .bookmarkAmount((Long) objects[15])
                .likeAmount((Long) objects[16])
                .commentAmount((Long) objects[17])
                .shareAmount((Long) objects[18])
                .isLiked((Boolean) objects[19])
                .isBookmarked((Boolean) objects[20])
                .isShared((Boolean) objects[21])
                .build();
    }

    private List<PostDto> getTransformList(List list) {
        Map<Long, PostDto> result = new TreeMap<>(Comparator.reverseOrder());
        for (Object obj : list) {
            PostDto postDto = (PostDto) obj;
            if (result.containsKey(postDto.getId())) {
                result.get(postDto.getId()).getMedia().removeAll(postDto.getMedia());
                result.get(postDto.getId()).getMedia().addAll(postDto.getMedia());
                result.get(postDto.getId()).getTags().removeAll(postDto.getTags());
                result.get(postDto.getId()).getTags().addAll(postDto.getTags());
            } else {
                result.put(postDto.getId(), postDto);
            }
        }
        return new ArrayList<>(result.values());
    }
}
