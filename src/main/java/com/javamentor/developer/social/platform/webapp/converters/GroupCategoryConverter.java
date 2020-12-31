package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.group.GroupCategoryDto;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupCategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class GroupCategoryConverter {

    private GroupCategoryService groupCategoryService;

    @Autowired
    protected void setGroupCategoryService (GroupCategoryService groupCategoryService){
        this.groupCategoryService = groupCategoryService;
    }

    @Mapping(source = "category", target = "category")
    public abstract GroupCategory toEntity( GroupCategoryDto groupCategoryDto);

    @Mapping(source = "category", target = "category")
    public abstract GroupCategoryDto toDto(GroupCategory groupCategory);
}
