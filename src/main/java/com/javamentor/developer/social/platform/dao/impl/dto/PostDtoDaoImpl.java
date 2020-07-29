package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.MediaPostDto;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

;

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
                    "m.url " +
                    "from Post p join p.user u join p.media m")
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
                            return PostDto.builder()
                                    .id((Long) objects[0])
                                    .title((String) objects[1])
                                    .text((String) objects[2])
                                    .userDto(userDto)
                                    .media(mediaPostDtoList)
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
}
