package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupUpdateInfoDto;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import com.javamentor.developer.social.platform.models.entity.group.GroupCategory;
import com.javamentor.developer.social.platform.service.abstracts.model.group.GroupCategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class GroupConverter {

    private GroupCategoryService groupCategoryService;

    @Autowired
    protected void setGroupCategoryService (GroupCategoryService groupCategoryService){
        this.groupCategoryService = groupCategoryService;
    }

    @Mapping(source = "groupCategory", target = "groupCategory.category")
    public abstract Group groupDtoToGroup(GroupDto groupDto);

    @Mapping(source = "groupCategory.category", target = "groupCategory")
    public abstract GroupDto groupToGroupDto(Group group);

    @Mapping(source = "groupCategory", target = "groupCategory",  qualifiedByName = "groupCategorySetter")
    public abstract Group groupUpdateInfoDtoToGroup(GroupUpdateInfoDto groupUpdateDto);

    @Mapping(source = "groupCategory.category", target = "groupCategory")
    public abstract GroupUpdateInfoDto groupToGroupUpdateInfoDto(Group group);

    @Named("groupCategorySetter")
    protected GroupCategory groupCategorySetter(String category){
        if(groupCategoryService.getGroupCategoryByName(category).isPresent()){
            return groupCategoryService.getGroupCategoryByName(category).get();
        }
        GroupCategory newGroupCategory = new GroupCategory();
        newGroupCategory.setCategory(category);
        groupCategoryService.create(newGroupCategory);
        return newGroupCategory;
    }


}


