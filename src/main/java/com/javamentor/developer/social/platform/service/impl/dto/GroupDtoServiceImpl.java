package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupDtoServiceImpl implements GroupDtoService {
    public final GroupDtoDao groupDtoDao;

    public final PostDtoDao postDtoDao;

    @Autowired
    public GroupDtoServiceImpl(GroupDtoDao groupDtoDao, PostDtoDao postDtoDao) {
        this.groupDtoDao = groupDtoDao;
        this.postDtoDao = postDtoDao;
    }

    @Override
    public List<GroupInfoDto> getAllGroups(int page, int size) {
        return groupDtoDao.getAllGroups(page, size);
    }

    @Override
    public Optional<GroupDto> getGroupById(Long id) {
        return groupDtoDao.getGroupById(id);
    }

    @Override
    public List<GroupWallDto> getPostsByGroupId(Long id, int page, int size) {
        return groupDtoDao.getPostsByGroupId(id, page, size);
    }

    @Override
    public Optional<GroupDto> getGroupByName(String name) {
        return groupDtoDao.getGroupByName(name);
    }

    @Override
    public List<UserDto> getUsersFromTheGroup(Long id, int page, int size) {
        return groupDtoDao.getUsersFromTheGroup(id, page, size);
    }
}
