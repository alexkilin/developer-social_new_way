package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PaginationDao;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.pagination.PaginationService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaginationServiceImpl<T, V> implements PaginationService<T, V> {
    private Map<String, PaginationDao> pageBeans = new ConcurrentHashMap<>();

    @Autowired
    public void PaginationService(Map<String, PaginationDao> pageBeans) {
        this.pageBeans = pageBeans;
    }

    @Override
    public PageDto<T, V> getPageDto(String methodName, Map<String, Object> parameters) {
        if (parameters.containsKey("currentPage" )&& parameters.get("currentPage") != null
                && parameters.containsKey("itemsOnPage") && parameters.get("itemsOnPage") != null) {

            PaginationDao paginationDao = pageBeans.get(methodName);
            return new PageDto<T, V>(
                    (int) parameters.get("currentPage"),
                    (int) parameters.get("itemsOnPage"),
                    paginationDao.getItems(parameters),
                    paginationDao.getCount(parameters));

        } else throw new PaginationException("Invalid pagination parameters. " +
                "Please, make sure that parameters contains not null keys 'currentPage' and 'itemsOnPage'");
    }
}