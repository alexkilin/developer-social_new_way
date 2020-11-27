package com.javamentor.developer.social.platform.dao.impl.dto.page.post;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("getAllTags")
public class PaginationGetAllTagsDaoImpl implements PaginationDao<TagDto> {
    @PersistenceContext
    private EntityManager entityManager;

    public PaginationGetAllTagsDaoImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TagDto> getItems(Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        return entityManager.createQuery(
                "SELECT " +
                        "id," +
                        "text" +
                        " FROM Tag")
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        if (objects[0] != null && objects[1] != null) {
                            return TagDto.builder()
                                    .id((Long) objects[0])
                                    .text((String) objects[1])
                                    .build();
                        } else return null;
                    }

                    @Override
                    public List transformList(List list) {
                        if (list.contains(null)) {
                            return new ArrayList();
                        } else return list;
                    }
                }).getResultList();
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (t) from Tag t",
                Long.class
        ).getSingleResult();
    }
}
