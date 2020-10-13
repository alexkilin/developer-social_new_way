package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
                    "u.city, " +
                    "u.role.name, " +
                    "u.status, " +
                    "u.active.name, " +
                    "u.profession " +
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
                                    .city((String) objects[9])
                                    .roleName(((String) objects[10]))
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
                    .getResultList();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return getAllUsers;
    }

    @Override
    public Optional<UserDto> getUserDtoById(Long id) {
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
                    "FROM User u WHERE u.userId = :paramId")
                    .setParameter("paramId", id)
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

     /*@Override
     public Optional<List<LanguageDto>> getUserLanguageDtoById(Long userId) {
         Optional<List<LanguageDto>> userLanguageDto = Optional.empty();
         try {
             userLanguageDto = Optional.of((List<LanguageDto>) entityManager.createNativeQuery("SELECT " +
                     "l.id, " +
                     "l.name " +
                     "FROM language l join user_languages ul  where ul.user_id = :paramId") //" ON u.language_id = l.id WHERE u.user_id = :paramId" JOIN u.languages as l)
                     .setParameter("paramId", userId)
                     .unwrap(Query.class)
                     .setResultTransformer(new ResultTransformer() {
                         @Override
                         public Object transformTuple(Object[] objects, String[] strings) {
                             return LanguageDto.builder()
                                    // .id(((Number) objects[0]).longValue())
                                     .name((String) objects[1])

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
         return userLanguageDto;
     }*/

}



