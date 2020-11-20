package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupDtoServiceImpl implements GroupDtoService {

    private final GroupDtoDao groupDtoDao;

    @Autowired
    public GroupDtoServiceImpl(GroupDtoDao groupDtoDao) {
        this.groupDtoDao = groupDtoDao;
    }

    @Override
    @Transactional
    public List<GroupInfoDto> getAllGroups(int page, int size) {
        return groupDtoDao.getAllGroups(page, size);
    }

    @Override
    @Transactional
    public Optional<GroupDto> getGroupById(Long id) {
        return groupDtoDao.getGroupById(id);
    }

    @Override
    @Transactional
    public List<GroupWallDto> getPostsByGroupId(Long id, int page, int size) {
        return groupDtoDao.getPostsByGroupId(id, page, size);
    }

    @Override
    @Transactional
    public Optional<GroupDto> getGroupByName(String name) {
        return groupDtoDao.getGroupByName(name);
    }

    @Override
    @Transactional
    public List<UserDto> getUsersFromTheGroup(Long id, int page, int size) {
        return groupDtoDao.getUsersFromTheGroup(id, page, size);
    }
}
