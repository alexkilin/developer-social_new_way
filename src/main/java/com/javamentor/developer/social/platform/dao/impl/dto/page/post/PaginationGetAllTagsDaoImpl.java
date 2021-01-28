package com.javamentor.developer.social.platform.dao.impl.dto.page.post;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.TagDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
                "select " +
                        "t.id," +
                        "t.text " +
                        "from Tag t " +
                        "order by t.id asc")
                .setFirstResult((currentPage - 1) * itemsOnPage)
                .setMaxResults(itemsOnPage)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                            return TagDto.builder()
                                    .id((Long) objects[0])
                                    .text((String) objects[1])
                                    .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
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
