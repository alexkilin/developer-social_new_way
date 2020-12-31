package com.javamentor.developer.social.platform.service.abstracts.model.group;

import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.List;
import java.util.Optional;

public interface GroupCategoryService extends GenericService<GroupCategory, Long> {

    Optional<GroupCategory> getByCategory( String category );

    boolean createCategory( GroupCategory category );

    int deleteCategory( GroupCategory category );

    List<GroupCategory> getAllCategories();
}
