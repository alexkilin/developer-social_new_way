package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AlbumImageDAOImpl extends GenericDaoAbstract<AlbumImage, Long> implements AlbumImageDAO {

    private final EntityManager manager;

    @Autowired
    public AlbumImageDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<AlbumImage> getAllByUserId(Long id) {
        Query q = manager.createQuery("SELECT " +
                "alb.id, " +
                "alb.persistDate, " +
                "alb.mediaType," +
                "alb.icon, " +
                "alb.name, " +
                "alb.lastRedactionDate " +
                "FROM Album as alb " +
                "WHERE alb.mediaType = 0 " +
                "AND alb.userOwnerId.userId = :id")
                .setParameter("id", id);
        List<AlbumImage> lst = q.getResultList();
        return lst;

    }
}
