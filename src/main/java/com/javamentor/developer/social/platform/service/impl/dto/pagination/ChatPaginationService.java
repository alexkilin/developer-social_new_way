package com.javamentor.developer.social.platform.service.impl.dto.pagination;

import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;

public class ChatPaginationService <T, V> extends PaginationServiceImpl<Object, Object> {

    @SuppressWarnings("unchecked")
    public PageDto<? extends T, ? extends V> getChatPageDto(String methodName, Map<String, Object> parameters) {
        PageDto<?, ?> pageDto;
        pageDto = super.getPageDto(methodName, parameters);
        return (PageDto<T, V>) pageDto;
    }
}
