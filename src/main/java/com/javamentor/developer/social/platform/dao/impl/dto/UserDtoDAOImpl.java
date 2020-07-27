package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.impl.model.ReadWriteDAOImpl;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDtoDAOImpl extends ReadWriteDAOImpl<UserDto, Long> implements UserDtoDao {

    @Override
    public List<UserDto> getUserDtoList() {
        List<UserDto> getAllUsers = new ArrayList<>();

        try {
            getAllUsers = entityManager.createQuery("SELECT " +
                    "u.userId, " +
                    "u.firstName, " +
                    "u.lastName, " +
                    "u.dateOfBirth, " +
                    "u.education, " +
                    "u.aboutMe, " +
                    "u.avatar, " +
                    "u.email, " +
                    "u.password, " +
                    "u.persistDate, " +
                    "u.lastRedactionDate, " +
                    "u.city, " +
                    "u.role.name, " +
                    "u.status, " +
                    "u.active " +
                    "FROM User u")
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
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
                                    .persistDate((LocalDateTime) objects[9])
                                    .lastRedactionDate((LocalDateTime) objects[10])
                                    .city((String) objects[11])
                                    .role((String) objects[12])
                                    .status((Status) objects[13])
                                    .active((Active) objects[14])
                                    .build();
                        }

                        @Override
                        public List transformList(List list) {
                            return list;
                        }
                    })
                    .getResultList();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return getAllUsers;
    }

    @Override
    public Optional<UserDto> getUserDtoById(Long id) {

        return entityManager.createQuery("SELECT " +
                "u.userId, " +
                "u.firstName, " +
                "u.lastName, " +
                "u.dateOfBirth, " +
                "u.education, " +
                "u.aboutMe, " +
                "u.avatar, " +
                "u.email, " +
                "u.password, " +
                "u.persistDate, " +
                "u.lastRedactionDate, " +
                "u.city, " +
                "u.role.name, " +
                "u.status, " +
                "u.active " +
                "FROM User u WHERE u.id = " + id)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
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
                                .persistDate((LocalDateTime) objects[9])
                                .lastRedactionDate((LocalDateTime) objects[10])
                                .city((String) objects[11])
                                .role((String) objects[12])
                                .status((Status) objects[13])
                                .active((Active) objects[14])
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getSingleResult();
    }

}
