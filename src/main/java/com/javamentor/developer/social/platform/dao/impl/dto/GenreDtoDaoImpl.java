package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GenreDtoDao;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.dto.media.music.GenreDto;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class GenreDtoDaoImpl implements GenreDtoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<GenreDto> getGenreByTitle(String title) {
        Query<GenreDto> query = entityManager.createQuery(
                "select c.id, c.title " +
                        "   from Genre c    " +
                        "where c.title =: title")
                .setParameter("title" , title)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {

                    @Override
                    public Object transformTuple( Object[] objects , String[] strings ) {
                        if(objects[0] != null && objects[1] != null) {
                            return GenreDto.builder()
                                    .id((Long) objects[0])
                                    .title((String) objects[1])
                                    .build();
                        } else return null;
                    }

                    @Override
                    public List transformList(List list ) {
                        return list;
                    }
                });
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
