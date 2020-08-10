package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.models.dto.comment.CommentDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class PostDtoDaoImpl implements PostDtoDao {

    final
    EntityManager entityManager;

    @Autowired
    public PostDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PostDto> getPosts() {
        List<PostDto> postDtoList = new ArrayList<>();

        try {
            postDtoList = entityManager.createQuery("select " +
                    "p.id, " +
                    "p.title, " +
                    "p.text, " +
                    "p.persistDate, " +
                    "p.lastRedactionDate, " +
                    "u.userId, " +
                    "u.firstName, " +
                    "u.lastName, " +
                    "u.avatar, " +
                    "m.mediaType, " +
                    "m.url, " +
                    "t.id," +
                    "t.text " +
                    "from Post p " +
                    "join p.user u " +
                    "join p.media m " +
                    "join p.tags t")
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            UserDto userDto = UserDto.builder()
                                    .userId((Long) objects[5])
                                    .firstName((String) objects[6])
                                    .lastName((String) objects[7])
                                    .avatar((String) objects[8])
                                    .build();
                            MediaPostDto mediaPostDto = MediaPostDto.builder()
                                    .mediaType(objects[9].toString())
                                    .content((String) objects[10])
                                    .build();
                            List<MediaPostDto> mediaPostDtoList = new ArrayList<>();
                            mediaPostDtoList.add(mediaPostDto);
                            TagDto tagDto = TagDto.builder()
                                    .id((Long) objects[11])
                                    .text((String) objects[12])
                                    .build();
                            List<TagDto> tagDtoList = new ArrayList<>();
                            tagDtoList.add(tagDto);
                            return PostDto.builder()
                                    .id((Long) objects[0])
                                    .title((String) objects[1])
                                    .text((String) objects[2])
                                    .userDto(userDto)
                                    .media(mediaPostDtoList)
                                    .tags(tagDtoList)
                                    .persistDate((LocalDateTime) objects[3])
                                    .lastRedactionDate((LocalDateTime) objects[4])
                                    .build();
                        }

                        @Override
                        public List transformList(List list) {
                            Map<Long, PostDto> result = new TreeMap<>(Comparator.reverseOrder());
                            for (Object obj : list) {
                                PostDto postDto = (PostDto) obj;
                                if (result.containsKey(postDto.getId())) {
                                    result.get(postDto.getId()).getMedia().addAll(postDto.getMedia());
                                } else {
                                    result.put(postDto.getId(), postDto);
                                }
                            }
                            return new ArrayList<>(result.values());
                        }
                    })
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDtoList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CommentDto> getCommentsByPostId(Long id) {
        Query queryCommentsForPost = (Query) entityManager.createQuery(
                "SELECT " +
                        "c.id, " +
                        "c.persistDate, " +
                        "c.lastRedactionDate, " +
                        "c.comment, " +//3
                        "(SELECT u.lastName FROM User u WHERE c.user.userId = u.userId), " +
                        "(SELECT u.firstName FROM User u WHERE c.user.userId = u.userId), " +
                        "(SELECT u.userId FROM User u WHERE c.user.userId = u.userId)" +
                    "FROM Post p " +
                        "LEFT JOIN PostComment pc on p.id = pc.post.id " +
                        "LEFT JOIN Comment c on pc.comment.id = c.id " +
                    "WHERE p.id = :paramId")
                .setParameter("paramId", id);
        return queryCommentsForPost.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                UserDto userDto = UserDto.builder()
                        .userId((Long) objects[6])
                        .firstName((String) objects[5])
                        .lastName((String) objects[4])
                        .build();
                return CommentDto.builder()
                        .userDto(userDto)
                        .persistDate((LocalDateTime) objects[1])
                        .lastRedactionDate((LocalDateTime) objects[2])
                        .id((Long) objects[0])
                        .comment((String) objects[3])
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
    public List<MediaPostDto> getMediasByPostId(Long id) {
        Query queryMediasForPost = (Query) entityManager.createQuery(
                "SELECT " +
                        "m.mediaType, " +
                        "m.url " +
                    "FROM Post p " +
                        "LEFT JOIN p.media m " +
                    "WHERE p.id = :paramId")
                .setParameter("paramId", id);
        return queryMediasForPost.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                return MediaPostDto.builder()
                        .content((String) objects[1])
                        .mediaType(objects[0].toString())
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
    public List<TagDto> getTagsByPostId(Long id) {
        Query queryTagsForPost = (Query) entityManager.createQuery(
                "SELECT " +
                        "t.text, " +
                        "t.id " +
                    "FROM Post p " +
                        "LEFT JOIN p.tags t " +
                    "WHERE p.id = :paramId")
                .setParameter("paramId", id);
        return queryTagsForPost.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                return TagDto.builder()
                        .id((Long) objects[1])
                        .text((String) objects[0])
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        }).getResultList();
    }
}
