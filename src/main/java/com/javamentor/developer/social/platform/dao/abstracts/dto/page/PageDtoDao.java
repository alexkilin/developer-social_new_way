package com.javamentor.developer.social.platform.dao.abstracts.dto.page;

import java.util.List;
import java.util.Map;

public interface PageDtoDao <T> {
    List <T> getItems(Map<String, Object> parameters);
    Long getCount(Map<String, Object> parameters);
}
