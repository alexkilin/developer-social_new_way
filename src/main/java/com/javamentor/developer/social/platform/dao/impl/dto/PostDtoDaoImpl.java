package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import org.hibernate.query.Query;;
import org.hibernate.transform.ResultTransformer;

import java.util.List;

@Repository
public class PostDtoDaoImpl implements PostDtoDao {

    final
    EntityManager entityManager;

    @Autowired
    public PostDtoDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<PostDto> getPosts() {
        entityManager.createQuery("select " +
                "p.id," +
                "p.title," +
                "p.text," +
                "p.persist_date," +
                "p.last_redaction_date," +
                "p.user_id, " +
                "(select " +
                "u.first_name," +
                "u.last_name," +
                "from users u where u.id = p.user_id)," +
                "(select " +
                "pm.media_id from post_media pm where pm.post_id = p.id)," +
                "(select " +
                "m.url," +
                "m.type_id," +
                "m.persist_date from media m where m.id = pm.media_id)" +
                "from posts p")
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        return null;
                    }

                    @Override
                    public List transformList(List list) {
                        return null;
                    }
                });
        return null;
    }
}
