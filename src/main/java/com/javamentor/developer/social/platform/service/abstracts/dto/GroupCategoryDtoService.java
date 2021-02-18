package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.group.GroupCategoryDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;

import java.util.Map;
import java.util.Optional;

public interface GroupCategoryDtoService {

    Optional<GroupCategoryDto> getGroupCategoryByName( String category );

    PageDto<GroupCategoryDto, Object> getAllCategories( Map<String, Object> parameters );

}
