package com.javamentor.developer.social.platform.dao.impl.dto.page.user;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("getAllUsers")
public class PaginationGetAllUsersDaoImpl implements PaginationDao<UserDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAllUsersDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
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
                    .setFirstResult((currentPage - 1) * itemsOnPage)
                    .setMaxResults(itemsOnPage)
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
                                    .profession((String) objects[13])
                                    .activeName((String) objects[12])
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
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (u) FROM User u ",
                Long.class
        ).getSingleResult();
    }
}
