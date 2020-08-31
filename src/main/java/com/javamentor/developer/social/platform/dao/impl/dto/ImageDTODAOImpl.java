package com.javamentor.developer.social.platform.dao.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDTODAO;
import com.javamentor.developer.social.platform.models.dto.ImageDTO;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ImageDTODAOImpl implements ImageDTODAO {

    private final EntityManager manager;

    @Autowired
    public ImageDTODAOImpl(EntityManager manager) {
        this.manager = manager;
    }


    @Override
    public List<ImageDTO> getAllByUserId(Long id) {

        Query q = manager.createQuery(
                "SELECT img.id, " +
                        "img.media.url, " +
                        "img.description, " +
                        "img.media.persistDateTime " +
                        "FROM Image as img " +
                        "JOIN img.media " +
                        "WHERE img.id IN" +
                        "(SELECT m.id " +
                        "FROM Media as m " +
                        "WHERE m.mediaType = 0 " +
                        "AND m.user.userId = :id)")
                .setParameter("id", id)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] objects, String[] strings) {
                        Long id = (Long) objects[0];
                        String url = (String) objects[1];
                        String descr = (String) objects[2];
                        LocalDateTime time = (LocalDateTime) objects[3];

                        return ImageDTO.builder()
                                .id(id)
                                .url(url)
                                .description(descr)
                                .persistDateTime(time)
                                .build();
                    }

                    @Override
                    public List transformList(List list) {
                        return list;
                    }
                });

        List lst = q.getResultList();

        return lst;

    }
}
