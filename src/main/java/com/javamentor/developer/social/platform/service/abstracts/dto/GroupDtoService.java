package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;

import java.util.Map;
import java.util.Optional;

public interface GroupDtoService {

    PageDto<GroupInfoDto, ?> getAllGroups(Map<String, Object> parameters);

    Optional<GroupDto> getGroupById(Long id);

    PageDto<GroupWallDto, ?> getPostsByGroupId(Map<String, Object> parameters);

    Optional<GroupDto> getGroupByName(String name);

    PageDto<UserDto, ?> getUsersFromTheGroup(Map<String, Object> parameters);
}
