package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.page.PageDtoDao;
import com.javamentor.developer.social.platform.dao.impl.dto.page.PageDtoGetAudioOfAuthorDaoImpl;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import javassist.tools.reflect.Reflection;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageDtoService {
//    Map <> beans = new HashMap();
    ApplicationContext applicationContext;

    @Autowired
    public void pageDtoService(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @SneakyThrows
    public PageDto getPageDto(Class <? extends PageDtoDao> aClass, Map <String, Object> parameters, int currentPage, int itemsOnPage){
        PageDtoDao pageDtoDao = applicationContext.getBean(aClass);

        return new PageDto<>(currentPage, itemsOnPage, pageDtoDao.getItems(parameters, currentPage, itemsOnPage), pageDtoDao.getCount(parameters));

    }
}
