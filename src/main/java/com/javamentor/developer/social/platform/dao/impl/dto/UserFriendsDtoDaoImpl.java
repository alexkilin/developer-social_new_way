package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserFriendsDtoDao;
import com.javamentor.developer.social.platform.models.dto.FriendDto;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserFriendsDtoDaoImpl implements UserFriendsDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<FriendDto> getUserFriendsDtoById(Long id) {
        List<FriendDto> userFriends = new ArrayList<>();

        try {
            userFriends = entityManager.createQuery("select f.id, f.user.userId, f.friend.userId " +
                    "from Friend f " +
                    "where f.user.userId = " + id)
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            return FriendDto.builder()
                                    .id((Long) objects[0])
                                    .userId((Long) objects[1])
                                    .friendId((Long) objects[2])
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
}
