package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;

import java.util.List;

public interface GroupDtoService {
    List<GroupInfoDto> getAllGroups();
}
