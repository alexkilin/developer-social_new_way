package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupInfoDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupDtoServiceImpl implements GroupDtoService {
    public final GroupDtoDao groupDtoDao;

    @Autowired
    public GroupDtoServiceImpl(GroupDtoDao groupDtoDao) {
        this.groupDtoDao = groupDtoDao;
    }

    @Override
    public List<GroupInfoDto> getAllGroups() {
        return groupDtoDao.getAllGroups();
    }
}
