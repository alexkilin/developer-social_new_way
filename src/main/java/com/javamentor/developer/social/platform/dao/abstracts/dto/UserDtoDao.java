package com.javamentor.developer.social.platform.dao.abstracts.dto;


import com.javamentor.developer.social.platform.models.dto.LanguageDto;
import com.javamentor.developer.social.platform.models.dto.UserFriendDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoDao {
    List<UserDto> getUserDtoList(int currentPage, int itemsOnPage);
    Optional<UserDto> getUserDtoById(Long userId);
    List<LanguageDto> getUserLanguageDtoById(Long userId);
    List<UserFriendDto> getUserFriendsDtoById(Long userId, int currentPage, int itemsOnPage);
}
