package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;

import java.util.List;
import java.util.Optional;

public interface GroupDtoDao {
    List<GroupInfoDto> getAllGroups(int currentPage, int itemsOnPage);

    Optional<GroupDto> getGroupById(Long groupId);

    List<GroupWallDto> getPostsByGroupId(Long groupId, int currentPage, int itemsOnPage);

    Optional<GroupDto> getGroupByName(String name);

    List<UserDto> getUsersFromTheGroup(Long groupId, int currentPage, int itemsOnPage);
}
