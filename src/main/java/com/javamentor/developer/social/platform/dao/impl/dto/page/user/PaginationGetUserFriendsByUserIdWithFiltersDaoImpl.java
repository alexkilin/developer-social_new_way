package com.javamentor.developer.social.platform.dao.impl.dto.page.user;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.UserFriendDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("getUserFriendsWithFilters")
public class PaginationGetUserFriendsByUserIdWithFiltersDaoImpl implements PaginationDao<UserFriendDto> {

    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetUserFriendsByUserIdWithFiltersDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserFriendDto> getItems(Map<String, Object> parameters) {
        Long userId = (Long) parameters.get("userId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        List<UserFriendDto> userFriends = new ArrayList<>();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select " +
                    "f.friend.userId, " +
                    "f.friend.firstName, " +
                    "f.friend.avatar, " +
                    "f.friend.education, " +
                    "f.friend.profession, " +
                    "f.friend.status " +
                    "from Friend f " +
                    "where f.user.userId = :userId");
            Map<String, Object> filters = (Map<String, Object>) parameters.get("filters");

            for (Map.Entry<String, Object> tmp : filters.entrySet()) {
                if (!tmp.getValue().equals(null)) {
                    if (!tmp.getKey().equals("startDateOfBirth") || !tmp.getKey().equals("endDateOfBirth")) {
                        stringBuilder.append(" and f.user." + tmp.getKey() + " = " + tmp.getValue());
                    }
                }
                if (!filters.get("startDateOfBirth").equals(null) && !filters.get("endDateOfBirth").equals(null)){

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
                            return UserFriendDto.builder()
                                    .id((Long) objects[0])
                                    .fullName((String) objects[1])
                                    .avatar((String) objects[2])
                                    .education((String) objects[3])
                                    .profession((String) objects[4])
                                    .status((String) objects[5])
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
        return entityManager.createQuery(
                "select count (uf) FROM Friend uf where uf.user.userId = :userId",
                Long.class
        ).setParameter("userId", parameters.get("userId"))
                .getSingleResult();
    }
}