package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserFriendsDtoDao;
import com.javamentor.developer.social.platform.models.dto.FriendDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class UserFriendsDtoDaoImpl implements UserFriendsDtoDao {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public List<FriendDto> getUserFriendsDtoById(Long id) {
        List<FriendDto> userFriends = new ArrayList<>();

        try {
            userFriends = entityManager.createQuery("select * " +
                    "from friends f " +
                    "join users u " +
                    "on f.user_id = u.id" +
                    "where u.id = " + id)
                    .unwrap(Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public Object transformTuple(Object[] objects, String[] strings) {
                            return FriendDto.builder()
                                    .id(((Number) objects[0]).longValue())
                                    .user_id((User) objects[1].getId())
                                    .friend_id((User) objects[2].getId())
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
