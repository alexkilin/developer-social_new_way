package com.javamentor.developer.social.platform.dao.impl.model.media;

import com.javamentor.developer.social.platform.dao.abstracts.model.media.AlbumDAO;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.media.Album;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumDAOImpl extends GenericDaoAbstract<Album, Long> implements AlbumDAO {
}
