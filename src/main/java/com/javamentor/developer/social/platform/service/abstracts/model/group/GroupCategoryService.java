package com.javamentor.developer.social.platform.service.abstracts.model.group;

import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import com.javamentor.developer.social.platform.service.abstracts.GenericService;

import java.util.Optional;

public interface GroupCategoryService extends GenericService<GroupCategory, Long> {

    Optional<GroupCategory> getGroupCategoryByName( String category );

}
