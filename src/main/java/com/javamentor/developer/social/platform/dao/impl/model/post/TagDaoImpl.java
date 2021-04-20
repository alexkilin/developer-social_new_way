package com.javamentor.developer.social.platform.dao.impl.model.post;

import com.javamentor.developer.social.platform.dao.abstracts.model.post.TagDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.tags.MostPopularTagsInPostsDto;
import com.javamentor.developer.social.platform.models.entity.post.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl extends GenericDaoAbstract<Tag, Long> implements TagDao {


    @Override
    public Optional<Tag> getTagByText(String text) {
        TypedQuery<Tag> query = entityManager.createQuery("SELECT t FROM Tag t WHERE t.text = : text", Tag.class)
                                             .setParameter("text", text);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    @Override
    public List<Tag> getTagsByText(List<String> texts) {

        return entityManager.createQuery(
                "SELECT t FROM Tag t WHERE t.text IN (:texts) ORDER BY t.id ASC", Tag.class)
                .setParameter("texts", texts)
                .getResultList();
    }

    @Override
    public List<MostPopularTagsInPostsDto> getMostPopularTagsInPosts() {
        return entityManager.createNamedQuery("MostPopularTagsInPosts").getResultList();
    }


}
