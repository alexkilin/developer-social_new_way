package com.javamentor.developer.social.platform.dao.impl.dto.page.user;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import lombok.NoArgsConstructor;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@NoArgsConstructor
@Component("getAllUsersWithFilters")
public class PaginationGetUsersWithFiltersDaoImpl implements PaginationDao<UserDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<UserDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        List<UserDto> userFriends = new ArrayList<>();

        try {
            Map<String, Object> filters = (Map<String, Object>) parameters.get("filters");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SELECT " +
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
                    "FROM User u " +
                    "where u.userId is not Null");


            for (Map.Entry<String, Object> tmp : filters.entrySet()) {
                if (tmp.getValue() != null) {
                    if (tmp.getKey().equals("startDateOfBirth")) {
                        stringBuilder.append(" and u.dateOfBirth >= '" + filters.get("startDateOfBirth") + "'");
                    }
                    if (tmp.getKey().equals("endDateOfBirth")) {
                        stringBuilder.append(" and u.dateOfBirth <= '" + filters.get("endDateOfBirth") + "'");
                    }
                    if ("startDateOfBirth" != tmp.getKey().intern() && "endDateOfBirth" != tmp.getKey().intern()) {
                        stringBuilder.append(" and u." + tmp.getKey() + " = '" + tmp.getValue() + "'");
                    }
                }
            }


            userFriends = entityManager.createQuery(stringBuilder.append(" order by u.userId asc").toString())
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

        } catch (
                IllegalArgumentException e) {
            e.printStackTrace();
        }

        return userFriends;
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select count (u) FROM User u where u.userId is not Null");
        Map<String, Object> filters = (Map<String, Object>) parameters.get("filters");

        for (Map.Entry<String, Object> tmp : filters.entrySet()) {
            if (tmp.getValue() != null) {
                if (tmp.getKey().equals("startDateOfBirth")) {
                    stringBuilder.append(" and u.dateOfBirth >= '" + filters.get("startDateOfBirth") + "'");
                }
                if (tmp.getKey().equals("endDateOfBirth")) {
                    stringBuilder.append(" and u.dateOfBirth <= '" + filters.get("endDateOfBirth") + "'");
                }
                if ("startDateOfBirth" != tmp.getKey().intern() && "endDateOfBirth" != tmp.getKey().intern()) {
                    stringBuilder.append(" and u." + tmp.getKey() + " = '" + tmp.getValue() + "'");
                }
            }
        }

        return entityManager.createQuery(stringBuilder.toString(), Long.class)
                .getSingleResult();
    }
}