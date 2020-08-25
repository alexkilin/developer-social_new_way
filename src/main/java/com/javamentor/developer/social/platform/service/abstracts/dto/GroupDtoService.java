package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;

import java.util.List;

public interface GroupDtoService {
    List<GroupInfoDto> getAllGroups(int page, int size);

    GroupDto getGroupById(Long id);

    List<GroupWallDto> getPostsByGroupId(Long id, int page, int size);

    GroupInfoDto getGroupByName(String name);
}
