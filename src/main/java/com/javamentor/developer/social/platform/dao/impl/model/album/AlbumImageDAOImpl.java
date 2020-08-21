package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
        TypedQuery<AlbumImage> q = manager.createQuery(
                "SELECT albImg " +
                        "FROM AlbumImage AS albImg " +
                        "JOIN albImg.album " +
                        "WHERE albImg.id = " +
                        "(SELECT alb.id " +
                        "FROM Album AS alb " +
                        "WHERE alb.mediaType = 0 " +
                        "AND alb.userOwnerId.userId = :id)", AlbumImage.class)
                .setParameter("id", id);
        List<AlbumImage> lst = q.getResultList();
        return lst;

    }
}
