package com.javamentor.developer.social.platform.dao.impl.dto.page.pagePostImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllTags")
public class PageDtoGetAllTagsDaoImpl implements PageDtoDao<TagDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final PostDtoDao postDtoDao;

    @Autowired
    public PageDtoGetAllTagsDaoImpl(PostDtoDao postDtoDao) {
        this.postDtoDao = postDtoDao;
    }

    @Override
    public List<TagDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        List<TagDto> tagDtoList = postDtoDao.getAllTags(currentPage, itemsOnPage);
        return tagDtoList;
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (t) from Tag t",
                Long.class
        ).getSingleResult();
    }
}
