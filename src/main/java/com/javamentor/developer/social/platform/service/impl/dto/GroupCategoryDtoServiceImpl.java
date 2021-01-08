package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.GroupCategoryDtoDao;
import com.javamentor.developer.social.platform.models.dto.group.GroupCategoryDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.GroupCategoryDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupCategoryDtoServiceImpl extends PaginationServiceImpl<GroupCategoryDto, Object> implements GroupCategoryDtoService {

    @Autowired
    private GroupCategoryDtoDao groupCategoryDtoDao;


    @Override
    public Optional<GroupCategoryDto> getGroupCategoryByName( String category ) {
        return groupCategoryDtoDao.getGroupCategoryByName(category);
    }

    @Override
    public PageDto<GroupCategoryDto, Object> getAllCategories( Map<String, Object> parameters ) {
        return super.getPageDto("getAllCategories" , parameters);
    }

}
