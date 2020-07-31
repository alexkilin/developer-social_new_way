package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;

import java.util.List;

public interface GroupDtoDao {
    List<GroupInfoDto> getAllGroups(int page, int size);

    GroupDto getGroupById(Long id);
}
