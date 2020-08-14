package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.ImageDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
        Query q = manager.createQuery("SELECT " +
                "img.id, " +
                "img.description " +
                "FROM Image as img " +
                "WHERE img.id = " +
                "(SELECT m.id " +
                "FROM Media as m " +
                "WHERE m.mediaType = 0 " +
                "AND m.user.userId = :id)")
                .setParameter("id", id);
        List<Image> lst = q.getResultList();
        return lst;
    }
}
