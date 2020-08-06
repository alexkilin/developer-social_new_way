package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.FriendDto;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserFriendsMapper {

    List<FriendDto> toDto(List<Friend> friend);

    FriendDto toDto(Friend friend);
}
