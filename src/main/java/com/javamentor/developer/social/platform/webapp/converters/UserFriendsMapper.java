package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.FriendDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserFriendsMapper {

    UserFriendsMapper INSTANCE = Mappers.getMapper(UserFriendsMapper.class);

    FriendDto toDto(Friend friend);

    Friend toEntity(FriendDto friendDto);
}
