package com.javamentor.developer.social.platform.dao.impl.dto.page.pageUserImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.UserFriendDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getUserFriends")
public class PageDtoGetUserFriendsDtoByIdDaoImpl implements PageDtoDao<UserFriendDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserDtoDao userDtoDao;

    @Autowired
    public PageDtoGetUserFriendsDtoByIdDaoImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    public List<UserFriendDto> getItems(Map<String, Object> parameters) {
        return userDtoDao.getUserFriendsDtoById((Long) parameters.get("userId"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
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
