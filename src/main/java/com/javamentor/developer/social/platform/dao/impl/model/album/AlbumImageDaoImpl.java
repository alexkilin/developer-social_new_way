package com.javamentor.developer.social.platform.dao.impl.model.album;

import com.javamentor.developer.social.platform.dao.abstracts.model.album.AlbumImageDao;
import com.javamentor.developer.social.platform.dao.impl.GenericDaoAbstract;
import com.javamentor.developer.social.platform.models.entity.album.AlbumImage;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumImageDaoImpl extends GenericDaoAbstract<AlbumImage, Long> implements AlbumImageDao {
}
