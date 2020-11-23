package com.javamentor.developer.social.platform.dao.impl.dto.page.pageAlbumsImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.AlbumImageDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.AlbumImageDto;
import com.javamentor.developer.social.platform.models.entity.media.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllImageAlbumsOfUser")
public class PageDtoAlbumImageDaoImpl implements PageDtoDao<AlbumImageDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final AlbumImageDtoDao albumImageDtoDao;

    @Autowired
    public PageDtoAlbumImageDaoImpl(AlbumImageDtoDao albumImageDtoDao) {
        this.albumImageDtoDao = albumImageDtoDao;
    }

    @Override
    public List<AlbumImageDto> getItems(Map<String, Object> parameters) {
        return albumImageDtoDao.getAllByUserId((Long) parameters.get("userId"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (a) from Album a " +
                        "WHERE a.mediaType = :type " +
                        "AND a.userOwnerId.userId = :userId ",
                Long.class
        ).setParameter("userId", parameters.get("userId"))
                .setParameter("type", MediaType.IMAGE)
                .getSingleResult();
    }
}
