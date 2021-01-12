package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.GroupPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class GroupDtoServiceImpl extends GroupPaginationService<Object, Object> implements GroupDtoService {
    private final GroupDtoDao groupDtoDao;

    @Autowired
    public GroupDtoServiceImpl(GroupDtoDao groupDtoDao) {
        this.groupDtoDao = groupDtoDao;
    }

    @Override
    public PageDto<Object, Object> getAllGroups(Map<String, Object> parameters) {
        return super.getPageDto("getAllGroups", parameters);
    }

    @Override
    public Optional<GroupDto> getGroupById(Long id) {
        return groupDtoDao.getGroupById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<GroupWallDto, Object> getPostsByGroupId(Map<String, Object> parameters) {
        return (PageDto<GroupWallDto, Object>) super.getGroupPageDto("showGroupWall", parameters);
    }

    @Override
    public Optional<GroupDto> getGroupByName(String name) {
        return groupDtoDao.getGroupByName(name);
    }

    @Override
    public PageDto<Object, Object> getUsersFromTheGroup(Map<String, Object> parameters) {
        return super.getPageDto("getUsersFromTheGroup", parameters);
    }

    @Override
    public PageDto<Object, Object> getGroupsByCategory( Map<String, Object> parameters ) {
        return super.getPageDto("getGroupsByCategory" , parameters);
    }
}
