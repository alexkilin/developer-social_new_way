package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDAO;
import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumVideoDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import com.javamentor.developer.social.platform.models.entity.album.AlbumVideo;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumVideoDAOImpl extends GenericDaoAbstract<AlbumVideo, Long> implements AlbumVideoDAO {
}
