package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.developer.social.platform.models.dto.LanguageDto;
import com.javamentor.developer.social.platform.models.dto.UserFriendDto;
import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.UserDtoService;
import com.javamentor.developer.social.platform.service.impl.dto.pagination.PaginationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDtoServiceImpl extends PaginationServiceImpl<Object, Object> implements UserDtoService {
    private final UserDtoDao userDtoDao;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<UserDto, Object> getAllUserDto(Map<String, Object> parameters) {
        return (PageDto<UserDto, Object>) super.getPageDto("getAllUsers", parameters);
    }

    @Override
    public Optional<UserDto> getUserDtoById(Long id) {
        Optional<UserDto> userDto = userDtoDao.getUserDtoById(id);
        if (userDto.isPresent()) {
            List<LanguageDto> languages = userDtoDao.getUserLanguageDtoById(id);
            userDto.get().setLanguages(languages);
        }

        return userDto;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PageDto<UserFriendDto, Object> getUserFriendsDtoById(Map<String, Object> parameters) {
        return (PageDto<UserFriendDto, Object>) super.getPageDto("getUserFriends", parameters);
    }
}
