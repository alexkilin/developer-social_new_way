package com.javamentor.developer.social.platform.dao.impl.dto.page.group;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getGroupsByCategory")
public class PaginationGetAllGroupsByCategoryImpl implements PaginationDao<GroupInfoDto> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<? extends GroupInfoDto> getItems( Map<String, Object> parameters ) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        String category = (String) parameters.get("category");

        Query query = (Query) entityManager.createQuery(
                "SELECT DISTINCT " +
                        "g.id, " +
                        "g.name, " +
                        "gc.category, " +
                        "(SELECT COUNT(ghu.id) FROM ghu WHERE ghu.group.id = g.id), " +
                        "g.addressImageGroup " +
                        "FROM Group g JOIN GroupCategory gc ON gc.id = g.groupCategory.id " +
                        "JOIN GroupHasUser ghu ON g.id = ghu.group.id where g.groupCategory.category =: category")
                .setParameter("category" , category)
                .setFirstResult(( currentPage - 1 ) * itemsOnPage)
                .setMaxResults(itemsOnPage);
        return (List<GroupInfoDto>) query.unwrap(Query.class).setResultTransformer(new ResultTransformer() {

            @Override
            public Object transformTuple( Object[] objects , String[] strings ) {
                return GroupInfoDto.builder()
                        .id((Long) objects[0])
                        .name((String) objects[1])
                        .groupCategory((String) objects[2])
                        .subscribers((Long) objects[3])
                        .addressImageGroup((String) objects[4])
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
                "select count (g) from Group g" ,
                Long.class
        ).getSingleResult();
    }
}
