package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.GenreDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.genre.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class GenreDaoImpl extends GenericDaoAbstract<Genre, Long> implements GenreDao {
    @Override
    public Optional<Genre> getByTitle(String title) {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT a " +
                "FROM Genre AS a " +
                "WHERE a.title = :title", Genre.class)
                .setParameter("title", title);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
