package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.TagDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.post.Tag;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class TagDAOImpl extends GenericDaoAbstract<Tag, Long> implements TagDAO {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<Tag> getByName(String text) {
        TypedQuery<Tag> query = entityManager.createQuery(
                "SELECT r FROM Tag r " +
                        "WHERE r.text = :paramName", Tag.class)
                .setParameter("paramName", text.toLowerCase());
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
