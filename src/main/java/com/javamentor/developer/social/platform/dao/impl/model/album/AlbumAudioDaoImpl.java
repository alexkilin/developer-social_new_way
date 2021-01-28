package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumAudioDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.album.AlbumAudios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AlbumAudioDaoImpl extends GenericDaoAbstract<AlbumAudios, Long> implements AlbumAudioDao {

    private final AlbumDao albumDAO;

    @Autowired
    public AlbumAudioDaoImpl(AlbumDao albumDAO) {
        this.albumDAO = albumDAO;
    }

    @Override
    @Transactional
    public AlbumAudios createAlbumAudiosWithOwner(AlbumAudios albumAudios) {
        create(albumAudios);
        albumDAO.assignAlbumToUser(albumAudios.getAlbum());
        return albumAudios;
    }

    @Override
    public Optional<AlbumAudios> getByIdWithAudios(Long id) {
        TypedQuery<AlbumAudios> query = entityManager.createQuery("SELECT aa " +
                        "FROM AlbumAudios AS aa " + "" +
                        "LEFT JOIN FETCH aa.audios " +
                        "WHERE aa.id = :paramId", AlbumAudios.class)
                .setParameter("paramId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
