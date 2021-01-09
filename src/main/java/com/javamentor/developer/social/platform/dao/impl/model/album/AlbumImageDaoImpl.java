package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AlbumImageDaoImpl extends GenericDaoAbstract<AlbumImage, Long> implements AlbumImageDao {

    @Override
    public Optional<AlbumImage> getByIdWithAlbum(Long id) {
        TypedQuery<AlbumImage> query = entityManager.createQuery("SELECT ai " +
                        "FROM AlbumImage AS ai " +
                        "JOIN FETCH ai.album " +
                        "WHERE ai.id = :paramId", AlbumImage.class)
                .setParameter("paramId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
