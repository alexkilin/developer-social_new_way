package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.FriendDto;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import org.mapstruct.Mapper;

@Mapper
public interface UserFriendsMapper {

    FriendDto toDto(Friend friend);

    Friend toEntity(FriendDto friendDto);
}
