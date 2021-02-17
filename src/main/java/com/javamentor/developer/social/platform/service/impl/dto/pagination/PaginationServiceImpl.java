package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.pagination.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaginationServiceImpl<T, V> implements PaginationService<Object, Object> {

    private Map<String, PaginationDao> pageBeans;

    @Autowired
    public void setPageBeans(Map<String, PaginationDao> pageBeans) {
        this.pageBeans = pageBeans;
    }

    @Override
    public PageDto<T, V> getPageDto(String methodName, Map<String, Object> parameters) {
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");

        if (currentPage > 0 && itemsOnPage > 0) {
            PaginationDao paginationDao = pageBeans.get(methodName);
            PageDto<T, V> pageDto = new PageDto<T, V>(currentPage, itemsOnPage,
                    paginationDao.getItems(parameters), paginationDao.getCount(parameters));

            if (currentPage > pageDto.getTotalPages()) {
                throw new PaginationException("Invalid pagination parameters. " +
                        String.format("Parameter 'currentPage' value [%d] is greater than total number of available pages [%d] " +
                                "considering parameter 'itemsOnPage' value [%d]",
                                currentPage, pageDto.getTotalPages(), itemsOnPage));
            }

            return pageDto;
        }

        throw new PaginationException("Invalid pagination parameters. " +
                "Please, make sure that parameters 'currentPage' and 'itemsOnPage' values are greater than 0");
    }

    @Override
    public PageDto<T, V> getPageDtoWithFilterUsers(String methodName, Map<String, Object> parameters) {
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