package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.UserFriendDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoService {

    List<UserDto> getAllUserDto();

    Optional<UserDto> getUserDtoById(Long id);

    List<UserFriendDto> getUserFriendsDtoById(Long id, int currentPage, int itemsOnPage);
}
