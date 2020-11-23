package com.javamentor.developer.social.platform.dao.impl.dto.page.pageGroupImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getUsersFromTheGroup")
public class PageDtoGetUsersFromTheGroupDaoImpl implements PageDtoDao<UserDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final GroupDtoDao groupDtoDao;

    @Autowired
    public PageDtoGetUsersFromTheGroupDaoImpl(GroupDtoDao groupDtoDao) {
        this.groupDtoDao = groupDtoDao;
    }

    @Override
    public List<UserDto> getItems(Map<String, Object> parameters) {
        return groupDtoDao.getUsersFromTheGroup((Long) parameters.get("groupId"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (g) from User u join GroupHasUser g on u.userId = g.user.userId where g.group.id = :groupId",
                Long.class
        ).setParameter("groupId", parameters.get("groupId"))
                .getSingleResult();
    }
}
