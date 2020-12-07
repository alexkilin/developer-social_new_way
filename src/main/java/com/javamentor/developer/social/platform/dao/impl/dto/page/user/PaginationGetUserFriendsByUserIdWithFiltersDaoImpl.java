package com.javamentor.developer.social.platform.dao.impl.dto.page.user;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.UserFriendDtoWithFilter;
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

@NoArgsConstructor
@Component("getUserFriendsWithFilters")
public class PaginationGetUserFriendsByUserIdWithFiltersDaoImpl implements PaginationDao<UserFriendDtoWithFilter> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<UserFriendDtoWithFilter> getItems(Map<String, Object> parameters) {
        Long userId = (Long) parameters.get("userId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        List<UserFriendDtoWithFilter> userFriends = new ArrayList<>();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select " +
                    "f.friend.userId, " +
                    "f.friend.firstName, " +
                    "f.friend.avatar, " +
                    "f.friend.education, " +
                    "f.friend.profession, " +
                    "f.friend.status, " +
                    "f.friend.dateOfBirth " +
                    "from Friend f " +
                    "where f.user.userId = :userId");
            Map<String, Object> filters = (Map<String, Object>) parameters.get("filters");

            boolean checkForDateFilter = false;
            for (Map.Entry<String, Object> tmp : filters.entrySet()) {
                if (tmp.getValue() != null) {
                    if (tmp.getKey().equals("startDateOfBirth") || tmp.getKey().equals("endDateOfBirth")) {
                        if (checkForDateFilter == false) {
                            if (filters.get("startDateOfBirth") != null && filters.get("endDateOfBirth") != null) {
                                stringBuilder.append(" and f.friend.dateOfBirth between '" + filters.get("startDateOfBirth")
                                        + "' and '" + filters.get("endDateOfBirth") + "'");
                                checkForDateFilter = true;
                            } else {
                                if (filters.get("startDateOfBirth") != null) {
                                    stringBuilder.append(" and f.friend.dateOfBirth >= '" + filters.get("startDateOfBirth") + "'");
                                    checkForDateFilter = true;
                                } else if (filters.get("endDateOfBirth") != null) {
                                    stringBuilder.append(" and f.friend.dateOfBirth <= '" + filters.get("endDateOfBirth") + "'");
                                    checkForDateFilter = true;
                                }
                            }
                        }
                    } else {
                        stringBuilder.append(" and f.friend." + tmp.getKey() + " = '" + tmp.getValue() + "'");
                    }
                }
            }


            userFriends = entityManager.createQuery(String.valueOf(stringBuilder))
                    .setParameter("userId", userId)
                    .setFirstResult((currentPage - 1) * itemsOnPage)
                    .setMaxResults(itemsOnPage)
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            return UserFriendDtoWithFilter.builder()
                                    .id((Long) objects[0])
                                    .fullName((String) objects[1])
                                    .avatar((String) objects[2])
                                    .education((String) objects[3])
                                    .profession((String) objects[4])
                                    .status((String) objects[5])
                                    .dateOfBirth((Date) objects[6])
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
        stringBuilder.append("select count (uf) FROM Friend uf where uf.user.userId = :userId");
        Map<String, Object> filters = (Map<String, Object>) parameters.get("filters");

        boolean checkForDateFilter = false;
        for (Map.Entry<String, Object> tmp : filters.entrySet()) {
            if (tmp.getValue() != null) {
                if (tmp.getKey().equals("startDateOfBirth") || tmp.getKey().equals("endDateOfBirth")) {
                    if (checkForDateFilter == false) {
                        if (filters.get("startDateOfBirth") != null && filters.get("endDateOfBirth") != null) {
                            stringBuilder.append(" and uf.friend.dateOfBirth between '" + filters.get("startDateOfBirth")
                                    + "' and '" + filters.get("endDateOfBirth") + "'");
                            checkForDateFilter = true;
                        } else {
                            if (filters.get("startDateOfBirth") != null) {
                                stringBuilder.append(" and uf.friend.dateOfBirth >= '" + filters.get("startDateOfBirth") + "'");
                                checkForDateFilter = true;
                            } else if (filters.get("endDateOfBirth") != null) {
                                stringBuilder.append(" and uf.friend.dateOfBirth <= '" + filters.get("endDateOfBirth") + "'");
                                checkForDateFilter = true;
                            }
                        }
                    }
                } else {
                    stringBuilder.append(" and uf.friend." + tmp.getKey() + " = '" + tmp.getValue() + "'");
                }
            }
        }

        return entityManager.createQuery(stringBuilder.toString(), Long.class)
                .setParameter("userId", parameters.get("userId"))
                .getSingleResult();
    }
}