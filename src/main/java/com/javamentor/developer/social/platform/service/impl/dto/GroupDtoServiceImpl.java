package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.dao.abstracts.dto.PostDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.models.dto.group.GroupWallDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public GroupDto getGroupById(Long id) {
        return groupDtoDao.getGroupById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<GroupWallDto> getPostsByGroupId(Long id, int page, int size) {
        List<GroupWallDto> groupWallDtoList = groupDtoDao.getPostsByGroupId(id, page, size);

        for (GroupWallDto element : groupWallDtoList) {
            element.setMedia(postDtoDao.getMediasByPostId(element.getId()));
            element.setTags(postDtoDao.getTagsByPostId(element.getId()));
        }
        return groupWallDtoList;
    }

    @Override
    public GroupInfoDto getGroupByName(String name) {
        return groupDtoDao.getGroupByName(name).orElseThrow(IllegalArgumentException::new);
    }
}
