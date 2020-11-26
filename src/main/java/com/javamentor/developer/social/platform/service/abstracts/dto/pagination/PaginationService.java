package com.javamentor.developer.social.platform.service.abstracts.dto.pagination;

import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;

public interface PaginationService<T, V> {
    PageDto<T, V> getPageDto(String methodName, Map<String, Object> parameters);
}
