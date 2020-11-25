package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.page.GroupPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class GroupDtoServiceImpl extends GroupPaginationService implements GroupDtoService {
    private final GroupDtoDao groupDtoDao;

    @Autowired
    public GroupDtoServiceImpl(GroupDtoDao groupDtoDao) {
        this.groupDtoDao = groupDtoDao;
    }

    @Override
    @Transactional
    public PageDto<GroupInfoDto, ?> getAllGroups(Map<String, Object> parameters) {
        return super.getPageDto("getAllGroups", parameters);
    }

    @Override
    @Transactional
    public Optional<GroupDto> getGroupById(Long id) {
        return groupDtoDao.getGroupById(id);
    }

    @Override
    @Transactional
    public PageDto<GroupWallDto, ?> getPostsByGroupId(Map<String, Object> parameters) {
        return super.getGroupPageDto("showGroupWall", parameters);
    }

    @Override
    @Transactional
    public Optional<GroupDto> getGroupByName(String name) {
        return groupDtoDao.getGroupByName(name);
    }

    @Override
    @Transactional
    public PageDto<UserDto, ?> getUsersFromTheGroup(Map<String, Object> parameters) {
        return super.getPageDto("getUsersFromTheGroup", parameters);
    }
}
