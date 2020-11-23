package com.javamentor.developer.social.platform.dao.impl.dto.page.pageGroupImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllGroups")
public class PageDtoGetAllGroupsDaoImpl implements PageDtoDao<GroupInfoDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final GroupDtoDao groupDtoDao;

    @Autowired
    public PageDtoGetAllGroupsDaoImpl(GroupDtoDao groupDtoDao) {
        this.groupDtoDao = groupDtoDao;
    }

    @Override
    public List<GroupInfoDto> getItems(Map<String, Object> parameters) {
        return groupDtoDao.getAllGroups((int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (g) from Group g",
                Long.class
        ).getSingleResult();
    }
}
