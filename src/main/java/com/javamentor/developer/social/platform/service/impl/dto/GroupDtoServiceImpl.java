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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupDtoServiceImpl implements GroupDtoService {
    private final GroupDtoDao groupDtoDao;
    private final PostDtoDao postDtoDao;

    @Autowired
    public GroupDtoServiceImpl(GroupDtoDao groupDtoDao, PostDtoDao postDtoDao) {
        this.groupDtoDao = groupDtoDao;
        this.postDtoDao = postDtoDao;
    }

    @Override
    @Transactional
    public List<GroupInfoDto> getAllGroups(int currentPage, int itemsOnPage) {
        return groupDtoDao.getAllGroups(currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public Optional<GroupDto> getGroupById(Long id) {
        return groupDtoDao.getGroupById(id);
    }

    @Override
    @Transactional
    public List<GroupWallDto> getPostsByGroupId(Long id, int currentPage, int itemsOnPage) {
        return groupDtoDao.getPostsByGroupId(id, currentPage, itemsOnPage);
    }

    @Override
    @Transactional
    public Optional<GroupDto> getGroupByName(String name) {
        return groupDtoDao.getGroupByName(name);
    }

    @Override
    @Transactional
    public List<UserDto> getUsersFromTheGroup(Long id, int currentPage, int itemsOnPage) {
        return groupDtoDao.getUsersFromTheGroup(id, currentPage, itemsOnPage);
    }
}
