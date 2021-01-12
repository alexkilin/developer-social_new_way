package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.developer.social.platform.models.dto.LanguageDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.javamentor.developer.social.platform.dao.util.SingleResultUtil.getSingleResultOrNull;

@Repository
class UserDtoDaoImpl implements UserDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<UserDto> getUserDtoById(Long userId) {
        Optional<UserDto> userDto = Optional.empty();
        try {
            userDto = Optional.of((UserDto) entityManager.createQuery("SELECT " +
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
                    "u.role.name, " +
                    "u.status, " +
                    "u.profession, " +
                    "u.active.name " +
                    "FROM User u WHERE u.userId = :userId")
                    .setParameter("userId", userId)
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
                                    .city((String) objects[9])
                                    .roleName((String) objects[10])
                                    .status((String) objects[11])
                                    .profession((String) objects[12])
                                    .activeName((String) objects[13])
                                    .build();
                        }

                        @Override
                        public List transformList(List list) {
                            return list;
                        }
                    })
                    .getSingleResult());
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return userDto;
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<LanguageDto> getUserLanguageDtoById(Long userId) {

        List<LanguageDto> userLanguageDto;

        Query query = entityManager.createQuery("SELECT " +
                "l.id, " +
                "l.name " +
                "FROM User u LEFT JOIN u.languages l " +
                "WHERE u.userId = :userId " +
                "ORDER BY l.id ASC")
                .setParameter("userId", userId)
                .unwrap(Query.class);

        getSingleResultOrNull(query);

        userLanguageDto = query.setResultTransformer(new ResultTransformer() {
            @Override
            public Object transformTuple(Object[] objects, String[] strings) {
                return LanguageDto.builder()
                        .id(((Number) objects[0]).longValue())
                        .name((String) objects[1])
                        .build();
            }

            @Override
            public List transformList(List list) {
                return list;
            }
        })
                .getResultList();

        return userLanguageDto;
    }
}



