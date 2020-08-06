package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoDao {
    List<UserDto> getUserDtoList();
    Optional<UserDto> getUserDtoById(Long id);
}
