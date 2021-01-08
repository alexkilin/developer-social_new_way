package com.javamentor.developer.social.platform.dao.impl.dto.page.group;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupCategoryDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllCategories")
public class PaginationGetAllGroupCategoriesImpl implements PaginationDao<GroupCategoryDto> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<? extends GroupCategoryDto> getItems( Map<String, Object> parameters ) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        Query query = (Query) entityManager.createQuery(
                "SELECT DISTINCT " +
                        "g.id, " +
                        "g.category  FROM GroupCategory g")
                .setFirstResult(( currentPage - 1 ) * itemsOnPage)
                .setMaxResults(itemsOnPage);
        return (List<? extends GroupCategoryDto>) query.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple( Object[] objects , String[] strings ) {
                return GroupCategoryDto.builder()
                        .id((Long) objects[0])
                        .name((String) objects[1])
                        .build();
            }

            @Override
            public List transformList( List list ) {
                return list;
            }
        }).getResultList();
    }

    @Override
    public Long getCount( Map<String, Object> parameters ) {
        return entityManager.createQuery(
                "select count (g) from GroupCategory g" ,
                Long.class
        ).getSingleResult();
    }
}
