package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
                    "left join p.media m " +
                    "left join p.tags t")
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            MediaPostDto mediaPostDto = MediaPostDto.builder()
                                    .userId((Long) objects[5])
                                    .mediaType(objects[9] == null ? "null" : objects[9].toString())
                                    .url((String) objects[10])
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
                                    .userId((Long) objects[5])
                                    .firstName((String) objects[6])
                                    .lastName((String) objects[7])
                                    .avatar((String) objects[8])
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

    @SuppressWarnings("unchecked")
    @Override
    public List<PostDto> getPostsByTag(String text) {
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
                    "from Post as p " +
                    "join p.user as u " +
                    "join p.media as m " +
                    "left join p.tags as t " +
                    "where t.text = :text")
                    .setParameter("text", text)
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            MediaPostDto mediaPostDto = MediaPostDto.builder()
                                    .userId((Long) objects[5])
                                    .mediaType(objects[9] == null ? "null" : objects[9].toString())
                                    .url((String) objects[10])
                                    .build();
                            List<MediaPostDto> mediaPostDtoList = new ArrayList<>();
                            mediaPostDtoList.add(mediaPostDto);
                            TagDto tagDto = TagDto.builder()
                                    .id(objects[11] == null ? 0 : (Long) objects[11])
                                    .text(objects[12] == null ? "null" : (String) objects[12])
                                    .build();
                            List<TagDto> tagDtoList = new ArrayList<>();
                            tagDtoList.add(tagDto);
                            return PostDto.builder()
                                    .id((Long) objects[0])
                                    .title((String) objects[1])
                                    .text((String) objects[2])
                                    .userId((Long) objects[5])
                                    .firstName((String) objects[6])
                                    .lastName((String) objects[7])
                                    .avatar((String) objects[8])
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
//                            return list;
                        }
                    })
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDtoList;
    }
}
