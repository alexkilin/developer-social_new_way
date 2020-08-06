package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.ActiveDto;
import com.javamentor.developer.social.platform.models.dto.RoleDto;
import com.javamentor.developer.social.platform.models.dto.StatusDto;
import com.javamentor.developer.social.platform.models.dto.UserDto;
import com.javamentor.developer.social.platform.models.entity.user.Active;
import com.javamentor.developer.social.platform.models.entity.user.Role;
import com.javamentor.developer.social.platform.models.entity.user.Status;
import com.javamentor.developer.social.platform.models.entity.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    RoleDto toDto(Role role);

    Role toEntity(RoleDto roleDto);

    StatusDto toDto(Status status);

    Status toEntity(StatusDto statusDto);

    ActiveDto toDto(Active active);

    Active toEntity(ActiveDto activeDto);
}
