package com.javamentor.developer.social.platform.dao.abstracts.dto;


import com.javamentor.developer.social.platform.models.dto.LanguageDto;
import com.javamentor.developer.social.platform.models.dto.users.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserDtoDao {

    Optional<UserDto> getUserDtoById(Long userId);

    List<LanguageDto> getUserLanguageDtoById(Long userId);
}
