package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.ImageDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ImageDAOImpl extends GenericDaoAbstract<Image, Long> implements ImageDAO {
    private final EntityManager manager;

    @Autowired
    public ImageDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<Image> getAllByUserId(Long id) {
        TypedQuery<Image> q = manager.createQuery("SELECT " +
                "img " +
                "FROM Image as img " +
                "JOIN img.media " +
                "WHERE img.id = " +
                "(SELECT m.id " +
                "FROM Media as m " +
                "WHERE m.mediaType = 0 " +
                "AND m.user.userId = :id)", Image.class)
                .setParameter("id", id);

        List<Image> lst = q.getResultList();
        lst.forEach(i -> System.out.println(i.getId()));
        return lst;
    }
}
