package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDao;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumVideoDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.dao.util.SingleResultUtil;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AlbumVideoDaoImpl extends GenericDaoAbstract<AlbumVideo, Long> implements AlbumVideoDao {

    private final AlbumDao albumDAO;

    @Autowired
    public AlbumVideoDaoImpl(AlbumDao albumDAO){
        this.albumDAO = albumDAO;
    }

    @Override
    public AlbumVideo createAlbumVideoWithOwner(AlbumVideo albumVideo) {
        create(albumVideo);
        albumDAO.assignAlbumToUser(albumVideo.getAlbum());
        return albumVideo;
    }

    @Override
    public Optional<AlbumVideo> getByIdWithVideos(Long id) {
        TypedQuery<AlbumVideo> query = entityManager.createQuery("SELECT av " +
                        "FROM AlbumVideo AS av " +
                        "LEFT JOIN FETCH av.videos " +
                        "WHERE av.id = :paramId", AlbumVideo.class)
                .setParameter("paramId", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }
}
