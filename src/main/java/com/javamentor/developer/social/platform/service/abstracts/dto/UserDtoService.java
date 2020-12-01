package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.page.PageDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;

import java.util.Map;
import java.util.Optional;

public interface UserDtoService {

    PageDto<Object, Object> getAllUserDto(Map<String, Object> parameters);

    Optional<UserDto> getUserDtoById(Long id);

    PageDto<Object, Object> getUserFriendsDtoById(Map<String, Object> parameters);
}
