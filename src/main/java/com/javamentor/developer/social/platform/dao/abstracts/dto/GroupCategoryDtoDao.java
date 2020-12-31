package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.group.GroupCategoryDto;

import java.util.List;
import java.util.Optional;

public interface GroupCategoryDtoDao {

    List<GroupCategoryDto> getAllCategories();

    Optional <GroupCategoryDto> getByCategory(String category);
}
