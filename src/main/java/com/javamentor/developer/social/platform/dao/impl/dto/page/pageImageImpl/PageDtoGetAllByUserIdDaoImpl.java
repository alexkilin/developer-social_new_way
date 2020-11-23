package com.javamentor.developer.social.platform.dao.impl.dto.page.pageImageImpl;

import com.javamentor.developer.social.platform.dao.abstracts.dto.ImageDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.media.image.ImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Component("getAllImagesOfUser")
public class PageDtoGetAllByUserIdDaoImpl implements PageDtoDao<ImageDto> {
    @PersistenceContext
    private EntityManager entityManager;
    private final ImageDtoDao imageDtoDao;

    @Autowired
    public PageDtoGetAllByUserIdDaoImpl(ImageDtoDao imageDtoDao) {
        this.imageDtoDao = imageDtoDao;
    }

    @Override
    public List<ImageDto> getItems(Map<String, Object> parameters) {
        return imageDtoDao.getAllByUserId((Long) parameters.get("userId"),
                (int) parameters.get("currentPage"),
                (int) parameters.get("itemsOnPage"));
    }

    @Override
    public Long getCount(Map<String, Object> parameters) {
        return entityManager.createQuery(
                "select count (im) FROM Image im where im.media.user.userId = :userId ",
                Long.class
        ).setParameter("userId", parameters.get("userId"))
                .getSingleResult();
    }
}
