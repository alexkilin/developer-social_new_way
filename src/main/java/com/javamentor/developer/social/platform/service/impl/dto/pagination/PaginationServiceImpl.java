package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaginationServiceImpl<T, V> implements PaginationService<Object, Object> {
    private Map<String, PaginationDao> pageBeans;

    @Autowired
    public void setPageBeans(Map<String, PaginationDao> pageBeans) {
        this.pageBeans = pageBeans;
    }

    @Override
    public PageDto<? extends T, ? extends V> getPageDto(String methodName, Map<String, Object> parameters) {
        Object currentPage = parameters.get("currentPage");
        Object itemsOnPage = parameters.get("itemsOnPage");

        if (currentPage != null && itemsOnPage != null) {
            PaginationDao paginationDao = pageBeans.get(methodName);
            return new PageDto<T, V>(
                    (int) currentPage,
                    (int) itemsOnPage,
                    paginationDao.getItems(parameters),
                    paginationDao.getCount(parameters));

        } else throw new PaginationException("Invalid pagination parameters. " +
                "Please, make sure that parameters contains not null keys 'currentPage' and 'itemsOnPage'");
    }
}