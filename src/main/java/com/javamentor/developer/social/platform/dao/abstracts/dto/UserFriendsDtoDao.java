package com.javamentor.developer.social.platform.dao.abstracts.dto;

import com.javamentor.developer.social.platform.models.dto.FriendDto;

import java.util.List;

public interface UserFriendsDtoDao {

    List<FriendDto> getUserFriendsDtoById(Long id);

}
