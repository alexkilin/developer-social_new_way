package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupCategoryDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.group.GroupCategoryDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupCategoryDtoDaoImpl implements GroupCategoryDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GroupCategoryDto> getAllCategories() {
        return entityManager.createQuery("select c.id, c.category from GroupCategory c")
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple( Object[] objects , String[] strings ) {
                        if(objects[0] != null && objects[1] != null) {
                            return GroupCategoryDto.builder()
                                    .id((Long) objects[0])
                                    .category((String) objects[1])
                                    .build();
                        } else return null;
                    }

                    @Override
                    public List transformList( List list ) {
                        return list;
                    }
                }).getResultList();
    }

    @Override
    public Optional<GroupCategoryDto> getByCategory( String category ) {

        Query<GroupCategoryDto> query = entityManager.createQuery(
                "select c.id, c.category " +
                        "   from GroupCategory c    " +
                        "where c.category =: category")
                .setParameter("category" , category)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple( Object[] objects , String[] strings ) {
                        if(objects[0] != null && objects[1] != null) {
                            return GroupCategoryDto.builder()
                                    .id((Long) objects[0])
                                    .category((String) objects[1])
                                    .build();
                        } else return null;
                    }

                    @Override
                    public List transformList( List list ) {
                        return list;
                    }
                });
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
