package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupUpdateInfoDto;
import com.javamentor.developer.social.platform.models.entity.group.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class GroupConverter {

    @Mapping(source = "groupCategory", target = "groupCategory.category")
    public abstract Group groupDtoToGroup(GroupDto groupDto);

    @Mapping(source = "groupCategory.category", target = "groupCategory")
    public abstract GroupDto groupToGroupDto(Group group);

    @Mapping(source = "groupCategory", target = "groupCategory.category")
    public abstract Group groupUpdateInfoDtoToGroup(GroupUpdateInfoDto groupUpdateDto);

}


