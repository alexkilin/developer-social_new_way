package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.Album;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumDAOImpl extends GenericDaoAbstract<Album, Long> implements AlbumDAO {
}
