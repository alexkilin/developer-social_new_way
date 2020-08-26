package com.javamentor.developer.social.platform.webapp.converters;

import com.javamentor.developer.social.platform.models.dto.FriendDto;
import com.javamentor.developer.social.platform.models.entity.user.Friend;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserFriendsConverter {

    @Mappings({
            @Mapping(target = "userId", source = "user.userId"),
            @Mapping(target = "friendId", source ="friend.userId")
    })
    abstract FriendDto toDto(Friend friend);

    public abstract List<FriendDto> toDto(List<Friend> friend);
}
