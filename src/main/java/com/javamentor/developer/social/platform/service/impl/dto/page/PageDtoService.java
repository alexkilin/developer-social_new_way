package com.javamentor.developer.social.platform.service.impl.dto.page;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PageDtoService {
    private Map<String, PageDtoDao> pageBeans = new ConcurrentHashMap<>();

    @Autowired
    public void pageDtoService(Map<String, PageDtoDao> pageBeans) {
        this.pageBeans = pageBeans;
    }

    @SneakyThrows
    public PageDto getPageDto(Map<String, Object> parameters) {
        if (parameters.containsKey("currentPage")
                && parameters.containsKey("itemsOnPage")
                && parameters.containsKey("methodName")) {

            PageDtoDao pageDtoDao = pageBeans.get(parameters.get("methodName"));
            return new PageDto<>(
                    (int) parameters.get("currentPage"),
                    (int) parameters.get("itemsOnPage"),
                    pageDtoDao.getItems(parameters),
                    pageDtoDao.getCount(parameters));

        } else throw new IllegalArgumentException("Invalid page parameters");
    }
}
