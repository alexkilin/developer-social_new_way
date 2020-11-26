package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.group.GroupDto;

import java.util.Optional;

public interface GroupDtoDao {
    Optional<GroupDto> getGroupById(Long groupId);

    Optional<GroupDto> getGroupByName(String name);
}
