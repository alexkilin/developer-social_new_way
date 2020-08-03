package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;

import java.util.List;
import java.util.Optional;

public interface GroupDtoDao {
    List<GroupInfoDto> getAllGroups(int page, int size);

    Optional<GroupDto> getGroupById(Long id);
}
