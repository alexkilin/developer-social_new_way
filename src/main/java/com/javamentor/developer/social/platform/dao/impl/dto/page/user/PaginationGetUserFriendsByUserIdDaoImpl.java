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

@Component("getUserFriends")
public class PaginationGetUserFriendsByUserIdDaoImpl implements PaginationDao<UserFriendDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetUserFriendsByUserIdDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<UserFriendDto> getItems(Map<String, Object> parameters) {
        Long userId = (Long) parameters.get("userId");
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        List<UserFriendDto> userFriends = new ArrayList<>();

        try {
            userFriends = entityManager.createQuery(
                    "select " +
                            "f.friend.userId, " +
                            "f.friend.firstName, " +
                            "f.friend.avatar, " +
                            "f.friend.education, " +
                            "f.friend.profession, " +
                            "f.friend.status " +
                            "from Friend f " +
                            "where f.user.userId = :userId " +
                            "order by f.friend.userId asc")
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

        } catch (IllegalArgumentException e) {
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
