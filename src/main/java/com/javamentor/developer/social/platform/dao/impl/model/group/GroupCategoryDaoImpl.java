package com.javamentor.developer.social.platform.dao.impl.model.group;

import com.javamentor.developer.social.platform.dao.abstracts.model.group.GroupCategoryDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupCategoryDaoImpl extends GenericDaoAbstract<GroupCategory, Long> implements GroupCategoryDao {
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<GroupCategory> getByCategory( String category ) {
        TypedQuery<GroupCategory> query = entityManager.createQuery(
                "SELECT c FROM GroupCategory c " +
                        "WHERE c.category = :paramCategory" , GroupCategory.class)
                .setParameter("paramCategory" , category);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    public void createCategory( GroupCategory category ) {
        entityManager.persist(category);
    }

    public int deleteCategory(GroupCategory category){

        return entityManager.createQuery("DELETE FROM GroupCategory c    " +
                "where c.category =:paramCategory")
                .setParameter("paramCategory", category.getCategory()).executeUpdate();
    }


    public List<GroupCategory> getAllCategories(){
       return entityManager.createQuery("FROM GroupCategory", GroupCategory.class).getResultList();
    }

}
