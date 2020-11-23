package com.javamentor.developer.social.platform.dao.impl.dto.page.pageUserImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllUsers")
public class PageDtoGetUserDtoListDaoImpl implements PageDtoDao<UserDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserDtoDao userDtoDao;

    @Autowired
    public PageDtoGetUserDtoListDaoImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    public List<UserDto> getItems(Map<String, Object> parameters) {
        return userDtoDao.getUserDtoList((int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (u) FROM User u ",
                Long.class
        ).getSingleResult();
    }
}
