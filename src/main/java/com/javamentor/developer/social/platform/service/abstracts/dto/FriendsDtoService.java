package com.javamentor.developer.social.platform.service.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.FriendDto;

import java.util.List;

public interface FriendsDtoService {

    List<FriendDto> getUserFriendsDtoById(Long id);

}
