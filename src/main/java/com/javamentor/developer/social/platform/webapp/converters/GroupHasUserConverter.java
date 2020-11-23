package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.group.GroupHasUserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class GroupHasUserConverter {

    @Mapping(target = "id", source = "groupId")
    @Mapping(target = "groupHasUser", source = "bool")
    public abstract GroupHasUserInfoDto toGroupHasUserInfoDto(Long groupId, Boolean bool);
}
