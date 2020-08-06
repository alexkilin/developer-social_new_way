package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.UserDto;

import java.util.List;

public interface UserDtoDao {
    List<UserDto> getUserDtoList();
    UserDto getUserDtoById(Long id);
}
