package com.javamentor.developer.social.platform.dao.impl.model.user;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.models.entity.user.Status;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

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
                                    .roleName(((Role) objects[12]).getName())
                                    .statusName(((Status) objects[13]).getName())
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

        return Optional.of((UserDto) entityManager.createQuery("SELECT " +
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
                                .roleName(((Role) objects[12]).getName())
                                .statusName(((Status) objects[13]).getName())
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                })
                .getSingleResult());
    }

}
