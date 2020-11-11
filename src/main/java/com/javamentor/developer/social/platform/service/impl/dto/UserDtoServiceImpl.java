package com.javamentor.developer.social.platform.service.impl.dto;

import com.javamentor.developer.social.platform.dao.abstracts.dto.UserDtoDao;
import com.javamentor.developer.social.platform.models.dto.LanguageDto;
import com.javamentor.developer.social.platform.models.dto.UserFriendDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;
import com.javamentor.developer.social.platform.service.abstracts.dto.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDtoServiceImpl implements UserDtoService {

    private final UserDtoDao userDtoDao;

    @Autowired
    public UserDtoServiceImpl(UserDtoDao userDtoDao) {
        this.userDtoDao = userDtoDao;
    }

    @Override
    public List<UserDto> getAllUserDto() {
        return userDtoDao.getUserDtoList();
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
    public List<UserFriendDto> getUserFriendsDtoById(Long id, int currentPage, int itemsOnPage) {
        return userDtoDao.getUserFriendsDtoById(id, currentPage, itemsOnPage);
    }
}
